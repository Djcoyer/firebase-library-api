package com.library.firebaselibrary.controllers;

import com.library.firebaselibrary.models.User;
import com.library.firebaselibrary.models.auth.CreateUserRequest;
import com.library.firebaselibrary.models.auth.UpdateUserRequest;
import com.library.firebaselibrary.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);
        return user;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PATCH, produces = "application/json")
    public User updateUser(@PathVariable("userId") String userId, UpdateUserRequest request) {
        return userService.updateUser(request, userId);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }


}
