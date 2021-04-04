package com.example.library.controller;

import com.example.library.service.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintain")
public class MaintainController {
    @Autowired
    private MaintainService maintainService;

    @PutMapping("/borrowBook/{userId}/{bookId}")
    public ResponseEntity<String> borrowABook(@PathVariable long userId, @PathVariable long bookId) {
        return new ResponseEntity<>(maintainService.borrowBook(userId, bookId), HttpStatus.OK);
    }

    @PutMapping("/returnBook/{userId}/{bookId}")
    public ResponseEntity<String> returnABook(@PathVariable long userId, @PathVariable long bookId) {
        return new ResponseEntity<>(maintainService.returnABook(userId, bookId), HttpStatus.OK);
    }

    @PutMapping("/returnAllBooks/{userId}")
    public ResponseEntity<String> returnAllBooks(@PathVariable long userId) {
        return new ResponseEntity<>(maintainService.returnAllBooks(userId), HttpStatus.OK);
    }
}
