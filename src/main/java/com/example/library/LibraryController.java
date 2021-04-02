package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryController {
        @Autowired
        private LibraryService libraryService;

        @GetMapping("/viewBooks")
        public ResponseEntity<List<Library>> getAllBooks() {
            return new ResponseEntity<>(libraryService.findAll(), HttpStatus.OK);
        }

        @PostMapping("/addBooks")
        public  ResponseEntity<Library> addBooks(@RequestBody Library library) {
                return new ResponseEntity<>(libraryService.save(library), HttpStatus.CREATED);
        }
}
