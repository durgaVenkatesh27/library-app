package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.service.LibraryService;
import com.example.library.service.MaintainService;
import com.example.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MaintainController.class)
class MaintainControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private MaintainService maintainService;

    @Test
    void testBorrowABook() throws Exception{
        User addUser = new User(1L,"UserA");
        when(userService.findById(anyLong())).thenReturn(Optional.of(addUser));
        Book bookSample = new Book(1L,"Nature is God",true);
        when(libraryService.findAll()).thenReturn(Arrays.asList(bookSample));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/maintain/borrowBook/1/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }
}
