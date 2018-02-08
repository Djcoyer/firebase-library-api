package com.library.firebaselibrary.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.library.firebaselibrary.Exceptions.InternalServerException;
import com.library.firebaselibrary.models.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.firebaselibrary.helpers.FirestoreInit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReservationRepository {

    private final String reservations = "reservations";
    private Firestore db;
    private FirestoreInit firestoreInit;

    @Autowired
    public ReservationRepository(FirestoreInit firestoreInit) {
        this.firestoreInit = firestoreInit;
    }

    public ReservationDao findOne(String id) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<DocumentSnapshot> query = db.collection(reservations).document(id).get();
            DocumentSnapshot document = query.get();
            if(document.exists()){
                return document.toObject(ReservationDao.class);
            }else return null;
        }catch(Exception e) {
            throw new InternalServerException();
        }

    }

    public List<ReservationDao> findAllByUserId(String userId) {
        try{
            List<ReservationDao> reservationDaos = new ArrayList<>();
            if(db == null) db = firestoreInit.getFirestore();
            CollectionReference reservationCollection = db.collection(reservations);
            Query query = reservationCollection.whereEqualTo("userId", userId);
            ApiFuture<QuerySnapshot> future = query.get();
            for(DocumentSnapshot document : future.get().getDocuments()) {
                if(document.exists())
                    reservationDaos.add(document.toObject(ReservationDao.class));
            }
            return reservationDaos;
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public ReservationDao findByUserIdAndBookId(String userId, String bookId) {
        try{
            if(db == null) db = firestoreInit.getFirestore();
            CollectionReference reservationCollection = db.collection(reservations);
            Query query = reservationCollection.whereEqualTo("userId", userId).whereEqualTo("bookId", bookId);
            ApiFuture<QuerySnapshot> future = query.get();
            for(DocumentSnapshot document : future.get().getDocuments()) {
                if(document.exists()){
                    return document.toObject(ReservationDao.class);
                }
            }
            return  null;
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public List<ReservationDao> findAll() {
        try {
            List<ReservationDao> reservationDaos = new ArrayList<>();
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection(reservations).get();
            for(DocumentSnapshot document : future.get().getDocuments()) {
                if(document.exists())
                    reservationDaos.add(document.toObject(ReservationDao.class));
            }
            return reservationDaos;
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void save(ReservationDao reservationDao) {
        try {
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<WriteResult> future = db.collection(reservations)
                    .document(reservationDao.getReservationId()).set(reservationDao);
            future.get();

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public boolean exists(String id) {
        try {
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<DocumentSnapshot> query = db.collection(reservations).document(id).get();
            DocumentSnapshot document = query.get();
            return document.exists();

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void delete(String id) {
        try {
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<WriteResult> future = db.collection(reservations).document(id).delete();
            future.get();
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void deleteAllByBookId(String bookId){
        try {
            if(db == null) db = firestoreInit.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection(reservations).whereEqualTo("bookId", bookId).get();
            for(DocumentSnapshot document: future.get().getDocuments()) {
                if(document.exists()) {
                    db.collection(reservations).document(document.getId()).delete();
                }
            }
        }catch(Exception e) {
            throw new InternalServerException();
        }
    }
}
