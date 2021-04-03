package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L,"UserA",""));
        userList.add(new User(2L,"UserB",""));
        when(userService.findAll()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/viewUsers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void successfullyAddAUser() throws Exception{
        User addUser = new User("UserA");
        when(userService.save(any(User.class))).thenReturn(addUser);
        ObjectMapper objectMapper = new ObjectMapper();
        String addUserJSON = objectMapper.writeValueAsString(addUser);

        ResultActions result = mockMvc.perform(post("/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addUserJSON)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("UserA"));
    }
}
