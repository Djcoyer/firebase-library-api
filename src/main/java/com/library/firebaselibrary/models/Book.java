package com.library.firebaselibrary.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String bookId;
    private String title;
    private String author;
    private String description;
    private boolean available;
    private Date releaseDate;
    private String publisher;
    private String imgSrc;
}
