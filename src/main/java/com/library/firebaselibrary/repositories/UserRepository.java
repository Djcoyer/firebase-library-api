package com.library.firebaselibrary.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.library.firebaselibrary.Exceptions.InternalServerException;
import com.library.firebaselibrary.helpers.FirestoreInit;
import com.library.firebaselibrary.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserRepository {

    private Firestore db;
    private FirestoreInit firestoreInit;

    @Autowired
    public UserRepository(FirestoreInit firestoreInit) {
        this.firestoreInit = firestoreInit;
    }

    public boolean exists(String guid){
        return findOne(guid) != null;
    }

    public UserDao findOne(String guid) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<DocumentSnapshot> future = db.collection("users").document(guid).get();
            DocumentSnapshot doc = future.get();
            if(doc.exists())
            {
                return doc.toObject(UserDao.class);
            }
            else return null;
        }catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public UserDao findByEmailAddress(String emailAddress) {
        try {
            if (db == null) db = firestoreInit.getFirestore();
            CollectionReference usersRefrerence = db.collection("users");
            Query query = usersRefrerence.whereEqualTo("emailAddress", emailAddress);
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot querySnapshot = future.get();
            List<DocumentSnapshot> users = querySnapshot.getDocuments();
            if (users != null && users.size() != 0)
                return users.get(0).toObject(UserDao.class);
            else return null;
        } catch (InterruptedException e) {
            throw new InternalServerException();
        } catch (ExecutionException e) {
            throw new InternalServerException();
        }
    }

    public void save(UserDao userDao) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            DocumentReference documentReference = db.collection("users").document(userDao.getUserId());
            ApiFuture<WriteResult> result = documentReference.set(userDao);
            result.get();

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void delete(String guid) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            DocumentReference ref = db.collection("users").document(guid);
            ApiFuture<WriteResult> result = ref.delete();
            result.get();
        }catch(Exception e) {
            throw new InternalServerException();
        }
        return;
    }

    public boolean existsByEmailAddress(String emailAddress) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            CollectionReference usersCollection = db.collection("users");
            Query query = usersCollection.whereEqualTo("emailAddress", emailAddress);
            ApiFuture<QuerySnapshot> future = query.get();
            List<DocumentSnapshot> docs = future.get().getDocuments();
            return docs != null && docs.size() != 0;

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void insert(UserDao userDao) {
        try{
            if(db == null) db =  firestoreInit.getFirestore();
            ApiFuture<WriteResult> result = db.collection("users").document(userDao.getUserId()).set(userDao);
            WriteResult finished = result.get();
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

}
