package com.example.library;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Library {

    @Id
    @GeneratedValue
    private long id;
    private String bookTitle;
    private boolean isAvailable;

    public Library(){

    }

    public Library(String bookTitle, boolean isAvailable) {
        this.bookTitle = bookTitle;
        this.isAvailable = isAvailable;
    }

    public Library(Long id, String bookTitle, boolean isAvailable) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.isAvailable = isAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String text) {
        this.bookTitle = text;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean completed) {
        this.isAvailable = completed;
    }
}
