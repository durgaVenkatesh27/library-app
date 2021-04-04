package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.repository.LibraryRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.LibraryService;
import com.example.library.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testGetAllUsers() {
        User userSample = new User("Todo Sample 1");
        userRepository.save(userSample);
        UserService userService = new UserService(userRepository);

        List<User> usersList = userService.findAll();
        User lastToDo = usersList.get(usersList.size() - 1);

        assertEquals(userSample.getName(), lastToDo.getName());
        assertEquals(userSample.getId(), lastToDo.getId());
    }

    @Test
    void testGetAllUsersWithNoUsers() {
        UserService userService = new UserService(userRepository);
        List<User> usersList = userService.findAll();
        assertEquals(0, usersList.size());
    }

    @Test
    void testAddUser() {
        UserService userService = new UserService(userRepository);
        User userSample = new User("UserA");

        userService.save(userSample);

        assertEquals(1.0, userRepository.count());
    }

    @Test
    void testSingleUser() {
        User userSample = new User(1L, "Todo Sample 1");
        userRepository.save(userSample);
        UserService userService = new UserService(userRepository);

        User user = userService.findById(1).orElse(userSample);

        assertEquals(userSample.getName(), user.getName());
        assertEquals(userSample.getId(), user.getId());
    }
}
