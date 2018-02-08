package com.library.firebaselibrary.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.library.firebaselibrary.Exceptions.InternalServerException;
import com.library.firebaselibrary.helpers.FirestoreInit;
import com.library.firebaselibrary.models.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
public class BookRepository {
    private Firestore db;
    private FirestoreInit firestoreInit;

    @Autowired
    public BookRepository(FirestoreInit firestoreInit) {
        this.firestoreInit = firestoreInit;
    }

    public BookDao findOne(String id) {
        try {
            if (db == null) firestoreInit.getFirestore();
            DocumentReference docRef = db.collection("books").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(BookDao.class);
            } else return null;
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    //TODO: OPTIMIZE FIREBASE TO QUERY SUBDOCUMENT FOR SPECIFIC INFORMATION
    public BookDao getBookAggregateInfo(String guid) {
        BookDao bookDao = findOne(guid);
        if(bookDao != null)
        {
            BookDao returnBook = new BookDao();
            returnBook.setTitle(bookDao.getTitle());
            returnBook.setAuthor(bookDao.getAuthor());
            return returnBook;
        }
        else return null;
    }

    public List<BookDao> findAll() {
        if (db == null) db = firestoreInit.getFirestore();

        List<BookDao> bookDaos = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = db.collection("books").get();
            QuerySnapshot querySnapshot = query.get();
            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
            for (DocumentSnapshot document : documents) {
                bookDaos.add(document.toObject(BookDao.class));
            }
        } catch (Exception e) {
            throw new InternalServerException();
        }

        return bookDaos;
    }

    public List<BookDao> getAllByAvailable() {
        try{
            if(db == null) firestoreInit.getFirestore();
            List<BookDao> books = new ArrayList<>();
            CollectionReference booksCollection = db.collection("books");
            Query query = booksCollection.whereEqualTo("available", true);
            ApiFuture<QuerySnapshot> future = query.get();
            for(DocumentSnapshot document: future.get().getDocuments()) {
                if(document.exists()) {
                    books.add(document.toObject(BookDao.class));
                }
            }
            return books;

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public boolean existsByAuthorAndTitle(String author, String title) {
        try {
            if (db == null) firestoreInit.getFirestore();

            CollectionReference books = db.collection("books");
            Query query = books.whereEqualTo("author", author).whereEqualTo("title", title);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<DocumentSnapshot> docs = querySnapshot.get().getDocuments();
            return docs.size() != 0;

        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public void save(BookDao bookDao) {

        try {
            if (db == null) firestoreInit.getFirestore();
            ApiFuture<WriteResult> future = db.collection("books").document(bookDao.getBookId()).set(bookDao);
            future.get();
        } catch (Exception e) {
            throw new InternalServerException();
        }

    }

    public boolean exists(String id) {
        try {
            if(db == null) firestoreInit.getFirestore();
            ApiFuture<DocumentSnapshot> query = db.collection("books").document(id).get();
            DocumentSnapshot test = query.get();
            return test.exists();

        }catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public void delete(String id) {
        return;
    }

    public boolean getBookAvailable(String bookId) {
        return true;
    }
}
