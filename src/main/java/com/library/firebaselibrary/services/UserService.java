package com.library.firebaselibrary.services;

import com.library.firebaselibrary.Exceptions.ConflictException;
import com.library.firebaselibrary.Exceptions.InvalidInputException;
import com.library.firebaselibrary.Exceptions.NotFoundException;
import com.library.firebaselibrary.models.User;
import com.library.firebaselibrary.models.auth.AuthTokens;
import com.library.firebaselibrary.models.auth.CreateUserRequest;
import com.library.firebaselibrary.models.auth.LoginInfo;
import com.library.firebaselibrary.models.auth.UpdateUserRequest;
import com.library.firebaselibrary.models.dao.UserDao;
import com.library.firebaselibrary.transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.firebaselibrary.repositories.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    private AuthService authService;
    private UserRepository userRepository;

    @Autowired
    public UserService(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setEmailAddress(request.getEmailAddress());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setUserId(UUID.randomUUID().toString());
        if (userRepository.existsByEmailAddress(request.getEmailAddress()))
            throw new ConflictException();
        String auth0Id = authService.createAuth0User(user, request.getPassword(), request.isAdmin());
        user.setAuth0Id(auth0Id);
        UserDao userDao = UserTransformer.transform(user);
        userRepository.insert(userDao);

        return user;
    }

    public User updateUser(UpdateUserRequest request, String userId) {
        UserDao userDao = userRepository.findOne(userId);
        if(userDao == null)
            throw new NotFoundException();
        String auth0Id = userDao.getAuth0Id();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String emailAddress = request.getEmailAddress();

        if(emailAddress != null && ! emailAddress.equalsIgnoreCase(""))
            userDao.setEmailAddress(emailAddress);
        else request.setEmailAddress(null);
        if(firstName != null && !firstName.equalsIgnoreCase(""))
            userDao.setFirstName(firstName);
        else request.setFirstName(null);
        if(lastName != null && !lastName.equalsIgnoreCase(""))
            userDao.setLastName(lastName);
        else request.setLastName(null);
        authService.updateUser(request, auth0Id);

        userRepository.save(userDao);

        User user = UserTransformer.transform(userDao);
        return user;
    }

    public AuthTokens login(LoginInfo loginInfo) {
        UserDao userDao = userRepository.findByEmailAddress(loginInfo.getUsername());
        if(userDao == null)
            throw new NotFoundException();
        AuthTokens authTokens = authService.login(loginInfo);
        userDao.setRefreshToken(authTokens.getRefresh_token());
        userRepository.save(userDao);
        return authTokens;
    }

    public void logout(String authHeader) {
        String userId = authService.getUserIdFromAuthorizationHeader(authHeader);
        if(userId == null)
            throw new InvalidInputException();
        if(!userRepository.exists(userId))
            throw new NotFoundException();
        UserDao userDao = userRepository.findOne(userId);
        String refreshToken = userDao.getRefreshToken();
        if(refreshToken == null || refreshToken.equalsIgnoreCase(""))
            return;
        authService.revokeAuthRefreshToken(refreshToken);
        userDao.setRefreshToken(null);
        userRepository.save(userDao);
    }

    //region GET

    public User getUser(String customerId) {
        UserDao userDao = userRepository.findOne(customerId);
        if(userDao == null)
            throw new NotFoundException();
        return UserTransformer.transform(userDao);
    }

    //endregion

    //region DELETE
    public void deleteUser(String userId) {
        if(!userRepository.exists(userId))
            throw new NotFoundException();
        UserDao userDao = userRepository.findOne(userId);
        String auth0Id = userDao.getAuth0Id();
        authService.deleteUser(auth0Id);
        userRepository.delete(userId);
    }

    //endregion

    //region HELPERS
    public boolean userExists(String userId) {
        return userRepository.exists(userId);
    }

    //endregion

}
