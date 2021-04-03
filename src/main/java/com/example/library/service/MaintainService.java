package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintainService {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private UserService userService;

    public String borrowBook(long userId, long bookId){
        String responseMessage = "Sorry, Book is not available";
        User userInfo = userService.findById(userId).orElse(null);
        if(userInfo == null)
            return "User Not Available";
        List<Book> library = libraryService.findAll();
        List<String> bookList = userInfo.getBookList();
        for (Book book : library) {
            if (book.getId() == bookId && book.isIsAvailable() && bookList.size() < 2) {
                userInfo.setBooks((bookList.isEmpty() ? "" : "," )+ book.getId());
                userService.save(userInfo);
                book.setIsAvailable(false);
                libraryService.save(book);
                responseMessage = "Book has been added into borrowed list";
            } else if ( bookList.size() == 2){
                responseMessage = "Sorry, User Can borrow 2 book only";
            }
        }
        return responseMessage;
    }
}
