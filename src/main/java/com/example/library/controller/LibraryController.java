package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
        @Autowired
        private LibraryService libraryService;

        @GetMapping("/viewBooks")
        public ResponseEntity<List<Book>> getAllBooks() {
            return new ResponseEntity<>(libraryService.findAll(), HttpStatus.OK);
        }

        @PostMapping("/addBooks")
        public  ResponseEntity<Book> addBooks(@RequestBody Book book) {
                return new ResponseEntity<>(libraryService.save(book), HttpStatus.CREATED);
        }
}
