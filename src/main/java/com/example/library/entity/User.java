package com.example.library.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String books;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name, String books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public List<Long> getBookList() {
        if (this.getBooks() == null || this.getBooks().isEmpty())
            return Arrays.asList();
        return Arrays.asList(this.getBooks().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }

    public void setBookList(List<Long> bookList) {
        String bookNames = bookList.stream().map(String::valueOf)
                .collect(Collectors.joining(","));
        setBooks(bookNames);
    }
}
