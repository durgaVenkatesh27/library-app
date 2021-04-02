package com.example.library;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LibraryService {
    private LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    public Library save(Library Library) {
        return libraryRepository.save(Library);
    }
}
