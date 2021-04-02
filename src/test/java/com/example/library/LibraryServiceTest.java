package com.example.library;

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
    void tearDown(){
        libraryRepository.deleteAll();
    }

    @Test
    void testGetAllBooks(){
        Library librarySample = new Library("Todo Sample 1",true);
        libraryRepository.save(librarySample);
        LibraryService toDoService = new LibraryService(libraryRepository);

        List<Library> booksList = toDoService.findAll();
        Library lastToDo = booksList.get(booksList.size() - 1);

        assertEquals(librarySample.getBookTitle(), lastToDo.getBookTitle());
        assertEquals(librarySample.isIsAvailable(), lastToDo.isIsAvailable());
        assertEquals(librarySample.getId(), lastToDo.getId());
    }

    @Test
    void testGetAllBooksWithNoBooks(){
        LibraryService toDoService = new LibraryService(libraryRepository);
        List<Library> booksList = toDoService.findAll();
        assertEquals(0, booksList.size());
    }

    @Test
    void testAddBook() {
        LibraryService libraryService = new LibraryService(libraryRepository);
        Library bookSample = new Library("Nature is God",true);

        libraryService.save(bookSample);

        assertEquals(1.0, libraryRepository.count());
    }

}
