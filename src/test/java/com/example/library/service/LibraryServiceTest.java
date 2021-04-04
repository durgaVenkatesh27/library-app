package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.repository.LibraryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryServiceTest {
    @Autowired
    private LibraryRepository libraryRepository;


    @AfterEach
    void tearDown() {
        libraryRepository.deleteAll();
    }

    @Test
    void testGetAllBooks() {
        Book bookSample = new Book("Todo Sample 1", true);
        libraryRepository.save(bookSample);
        LibraryService toDoService = new LibraryService(libraryRepository);

        List<Book> booksList = toDoService.findAll();
        Book lastToDo = booksList.get(booksList.size() - 1);

        assertEquals(bookSample.getBookTitle(), lastToDo.getBookTitle());
        assertEquals(bookSample.isIsAvailable(), lastToDo.isIsAvailable());
        assertEquals(bookSample.getId(), lastToDo.getId());
    }

    @Test
    void testGetAllBooksWithNoBooks() {
        LibraryService toDoService = new LibraryService(libraryRepository);
        List<Book> booksList = toDoService.findAll();
        assertEquals(0, booksList.size());
    }


    @Test
    void testAddBook() {
        LibraryService libraryService = new LibraryService(libraryRepository);
        Book bookSample = new Book("Nature is God", true);

        libraryService.save(bookSample);

        assertEquals(1.0, libraryRepository.count());
    }

    @Test
    void testGetBook() {
        LibraryService libraryService = new LibraryService(libraryRepository);
        Book bookSample = new Book(1L, "Nature is God", true);
        libraryService.save(bookSample);

        Book book = libraryService.findById(1L).orElse(bookSample);

        assertEquals(bookSample.getBookTitle(), book.getBookTitle());
        assertEquals(bookSample.getId(), book.getId());
    }

}
