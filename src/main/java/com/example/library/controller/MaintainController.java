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
        public  ResponseEntity<String> borrowABook(@PathVariable long userId, @PathVariable long bookId) {
                 return new ResponseEntity<>(maintainService.borrowBook(userId, bookId), HttpStatus.OK);
        }
}
