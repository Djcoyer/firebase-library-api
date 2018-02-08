package com.library.firebaselibrary.models.dao;

import java.util.Date;

public class BookDao {
    private String bookId;
    private String title;
    private String author;
    private String description;
    private boolean available;
    private Date releaseDate;
    private String publisher;
    private String imgSrc;


    public BookDao() {

    }

    public BookDao(String bookId, String title, String author, String description, boolean available, Date releaseDate, String publisher, String imgSrc) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

