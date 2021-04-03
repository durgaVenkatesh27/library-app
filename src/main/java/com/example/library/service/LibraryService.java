package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.LibraryRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LibraryService {
    private LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> findAll() {
        return libraryRepository.findAll();
    }

    public Book save(Book book) {
        return libraryRepository.save(book);
    }
}
