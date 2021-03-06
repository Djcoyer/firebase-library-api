package com.library.firebaselibrary.services;


import com.library.firebaselibrary.Exceptions.ConflictException;
import com.library.firebaselibrary.Exceptions.InvalidInputException;
import com.library.firebaselibrary.Exceptions.NotFoundException;
import com.library.firebaselibrary.models.Book;
import com.library.firebaselibrary.models.dao.BookDao;
import com.library.firebaselibrary.repositories.BookRepository;
import com.library.firebaselibrary.transformers.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //region GET
    public Book getBook(String bookId) {
        BookDao bookDao = bookRepository.findOne(bookId);
        if (bookDao == null)
            throw new NotFoundException();
        Book book = BookTransformer.transform(bookDao);
        return book;
    }

    public Book getBookAggregateInfo(String bookId) {
        BookDao bookDao = bookRepository.getBookAggregateInfo(bookId);
        if (bookDao == null)
            throw new NotFoundException();
        return BookTransformer.transform(bookDao);
    }

    public ArrayList<Book> getBooks() {
        List<BookDao> bookDaos = bookRepository.findAll();
        ArrayList<Book> books = new ArrayList<>();
        if (bookDaos.size() == 0)
            return books;
        for (BookDao bookDao : bookDaos) {
            Book book = BookTransformer.transform(bookDao);
            books.add(book);
        }
        return books;
    }

    public ArrayList<Book> getAvailableBooks() {
        List<BookDao> bookDaos = bookRepository.getAllByAvailable();
        ArrayList<Book> books = new ArrayList<>();
        if (bookDaos.size() == 0)
            return books;
        for (BookDao bookDao : bookDaos) {
            Book book = BookTransformer.transform(bookDao);
            books.add(book);
        }
        return books;
    }

    //endregion

    public Book addBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().equalsIgnoreCase("")
                || book.getTitle() == null || book.getTitle().equalsIgnoreCase("")
                || book.getDescription() == null || book.getDescription().equalsIgnoreCase("")
                || book.getPublisher() == null || book.getReleaseDate() == null) {
            throw new InvalidInputException();
        }
        if (bookRepository.existsByAuthorAndTitle(book.getAuthor(), book.getTitle()))
            throw new ConflictException();
        book.setBookId(UUID.randomUUID().toString());
        BookDao bookDao = BookTransformer.transform(book);
        bookDao.setAvailable(true);
        bookDao.setBookId(UUID.randomUUID().toString());
        bookRepository.save(bookDao);
        book.setAvailable(bookDao.isAvailable());
        return book;
    }

    public void deleteBook(String bookId) {
        if (!bookRepository.exists(bookId))
            throw new NotFoundException();
        bookRepository.delete(bookId);
    }

    public Book updateBook(Book book, String bookId) {
        if (!bookRepository.exists(bookId))
            throw new NotFoundException();
        BookDao bookDao = bookRepository.findOne(bookId);

        String description = book.getDescription();
        String title = book.getTitle();
        String author = book.getAuthor();
        if (description != null && !description.equalsIgnoreCase(""))
            bookDao.setDescription(description);
        if (title != null && !title.equalsIgnoreCase(""))
            bookDao.setTitle(title);
        if (author != null && !author.equalsIgnoreCase(""))
            bookDao.setAuthor(author);

        bookRepository.save(bookDao);
        book = BookTransformer.transform(bookDao);
        return book;
    }

    public void setBookAvailable(String bookId, boolean isAvailable) {
        if (!bookRepository.exists(bookId))
            throw new NotFoundException();
        BookDao bookDao = bookRepository.findOne(bookId);
        bookDao.setAvailable(isAvailable);
        bookRepository.save(bookDao);
    }

    //region HELPERS

    public boolean isBookAvailable(String bookId) {
        Boolean available = bookRepository.getBookAvailable(bookId);
        return available;
    }

    public boolean bookExists(String bookId) {
        return bookRepository.exists(bookId);
    }

    //endregion

}
