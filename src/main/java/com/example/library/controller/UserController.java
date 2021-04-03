package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
        @Autowired
        private UserService userService;

        @GetMapping("/viewUsers")
        public ResponseEntity<List<User>> getAllBooks() {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }

        @PostMapping("/addUser")
        public  ResponseEntity<User> addUser(@RequestBody User user) {
                return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
}
