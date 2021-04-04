package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class MaintainServiceTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MaintainService maintainService;


    @MockBean
    private LibraryService libraryService;

    @Test
    void testBorrowABookSuccess() {

        User userSample = new User(101L, "UserA");

        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));
        Book bookSample = new Book(1L, "Nature is God", true);
        when(libraryService.findAll()).thenReturn(Arrays.asList(bookSample));

        String response = maintainService.borrowBook(101, 1);

        assertEquals("Book has been added into borrowed list", response);
    }

    @Test
    void testBorrowABookWithNoUser() {

        Book bookSample = new Book(1L, "Nature is God", true);
        when(libraryService.findAll()).thenReturn(Arrays.asList(bookSample));

        String response = maintainService.borrowBook(101, 1);

        assertEquals("User Not Available", response);
    }

    @Test
    void testBorrowABookWithNoBook() {

        User userSample = new User(101L, "UserA");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));

        String response = maintainService.borrowBook(101, 1);

        assertEquals("Sorry, Book is not available", response);
    }

    @Test
    void testBorrowABookAfterLimit() {

        List<Book> booksList = new ArrayList<>();
        booksList.add(new Book(1L, "Nature is God", false));
        booksList.add(new Book(2L, "I am the God", false));
        booksList.add(new Book(3L, "I am the God - 2", true));
        when(libraryService.findAll()).thenReturn(booksList);

        User userSample = new User(101L, "UserA", "1,2");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));

        String response = maintainService.borrowBook(101, 3);

        assertEquals("Sorry, User Can borrow 2 book only", response);
    }

    @Test
    void testBorrowSameCopyOfBooks() {

        List<Book> booksList = new ArrayList<>();
        Book book = new Book(1L, "I am the God", false);
        booksList.add(book);
        booksList.add(new Book(2L, "I am the God", true));
        when(libraryService.findAll()).thenReturn(booksList);

        User userSample = new User(101L, "UserA", "1");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));
        when(libraryService.findById(anyLong())).thenReturn(Optional.of(book));
        String response = maintainService.borrowBook(101, 2);

        assertEquals("Sorry, User Can borrow a Copy of Book", response);
    }

    @Test
    void testReturnBooks() {

        Book book = new Book(1L, "I am the God", false);
        User userSample = new User(101L, "UserA", "1");

        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));
        when(libraryService.findById(anyLong())).thenReturn(Optional.of(book));
        String response = maintainService.returnABook(101, 1);

        assertEquals("Book returned successfully", response);
    }

    @Test
    void testReturnBooksWithInvalidUser() {
        Book book = new Book(1L, "I am the God", false);
        when(libraryService.findById(anyLong())).thenReturn(Optional.of(book));
        String response = maintainService.returnABook(102, 1);

        assertEquals("User Not Available", response);
    }

    @Test
    void testReturnBooksWithInvalidBook() {
        User userSample = new User(101L, "UserA", "1");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));
        String response = maintainService.returnABook(102, 1);

        assertEquals("Invalid Book", response);
    }

    @Test
    void testReturnAllBooks() {

        Book book = new Book(1L, "I am the God", false);
        User userSample = new User(101L, "UserA", "1");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));
        when(libraryService.findById(anyLong())).thenReturn(Optional.of(book));
        String response = maintainService.returnAllBooks(1);

        assertEquals("All Book returned successfully", response);
    }

    @Test
    void testReturnAllBooksWithInvalidUser() {
        Book book = new Book(1L, "I am the God", false);
        when(libraryService.findById(anyLong())).thenReturn(Optional.of(book));
        String response = maintainService.returnAllBooks(1);

        assertEquals("User Not Available", response);
    }

    @Test
    void testReturnAllBooksWithNoBooks() {
        User userSample = new User(101L, "UserA", "");
        when(userService.findById(anyLong())).thenReturn(Optional.of(userSample));

        String response = maintainService.returnAllBooks(1);

        assertEquals("User not having any Books", response);
    }

}
