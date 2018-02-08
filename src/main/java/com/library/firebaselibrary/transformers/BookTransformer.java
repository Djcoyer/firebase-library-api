package com.library.firebaselibrary.transformers;

import com.library.firebaselibrary.Exceptions.InternalServerException;
import com.library.firebaselibrary.models.Book;
import com.library.firebaselibrary.models.dao.BookDao;

public class BookTransformer {

    public static Book transform(BookDao bookDao) {
        Book book = new Book();
        try{
            book.setAuthor(bookDao.getAuthor());
            book.setBookId(bookDao.getBookId());
            book.setTitle(bookDao.getTitle());
            book.setDescription(bookDao.getDescription());
            book.setAvailable(bookDao.isAvailable());
            book.setImgSrc(bookDao.getImgSrc());
            book.setReleaseDate(bookDao.getReleaseDate());
            book.setPublisher(bookDao.getPublisher());
            return book;
        }
        catch(Exception e) {
            throw new InternalServerException();
        }
    }

    public static BookDao transform(Book book) {
        try{
            BookDao bookDao = new BookDao();
            bookDao.setAuthor(book.getAuthor());
            bookDao.setBookId((book.getBookId() == null ? "" : book.getBookId()));
            bookDao.setTitle(book.getTitle());
            bookDao.setDescription(book.getDescription());
            bookDao.setAvailable(book.isAvailable());
            bookDao.setImgSrc(book.getImgSrc());
            bookDao.setReleaseDate(book.getReleaseDate());
            bookDao.setPublisher(book.getPublisher());
            return bookDao;
        } catch(Exception e){
            throw new InternalServerException();
        }
    }
}
