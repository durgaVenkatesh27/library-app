package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
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
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> booksList = new ArrayList<>();
        booksList.add(new Book(1L,"Nature is God",true));
        booksList.add(new Book(2L,"I am the God",true));
        when(libraryService.findAll()).thenReturn(booksList);
        mockMvc.perform(MockMvcRequestBuilders.get("/library/viewBooks")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void successfullyAddABook() throws Exception{
        Book addBook = new Book(1L, "You Cant Predict Me", true);
        when(libraryService.save(any(Book.class))).thenReturn(addBook);
        ObjectMapper objectMapper = new ObjectMapper();
        String addBookJSON = objectMapper.writeValueAsString(addBook);

        ResultActions result = mockMvc.perform(post("/library/addBooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addBookJSON)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookTitle").value("You Cant Predict Me"))
                .andExpect(jsonPath("$.isAvailable").value(true));
    }
}
