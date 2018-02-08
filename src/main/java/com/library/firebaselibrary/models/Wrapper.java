package com.library.firebaselibrary.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Wrapper {

    @JsonProperty("Title")
    private String Title;

    @JsonProperty("Author")
    private String Author;

    @JsonProperty("Description")
    private String Description;

    public Wrapper() {

    }

    public Wrapper(String id, String title, String author, String description) {
        this.Title = title;
        this.Author = author;
        this.Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
