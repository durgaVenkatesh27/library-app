package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaintainService {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private UserService userService;

    @Value("${user.bookLimit}")
    private int bookLimit;

    private static final String USER_NOT_AVAILABLE = "User Not Available";

    public String borrowBook(long userId, long bookId) {
        String responseMessage = "Sorry, Book is not available";
        User userInfo = getUser(userId);

        if (userInfo == null)
            return USER_NOT_AVAILABLE;

        List<Book> library = libraryService.findAll();
        List<Long> bookList = new ArrayList<>(userInfo.getBookList());
        for (Book book : library) {
            if (book.getId() == bookId
                    && book.isIsAvailable() && bookList.size() < bookLimit) {
                if (isBookValidForUser(bookList, book.getBookTitle())) {
                    bookList.add(book.getId());
                    userInfo.setBookList(bookList);
                    userService.save(userInfo);
                    book.setIsAvailable(false);
                    libraryService.save(book);
                    return "Book has been added into borrowed list";
                } else {
                    return "Sorry, User Can borrow a Copy of Book";
                }
            } else if (bookList.size() == 2) {
                return "Sorry, User Can borrow 2 book only";
            }
        }
        return responseMessage;
    }

    public String returnABook(long userId, long bookId) {
        User user = getUser(userId);

        if (user == null)
            return USER_NOT_AVAILABLE;

        Book book = libraryService.findById(bookId).orElse(null);
        if (book == null)
            return "Invalid Book";
        List<Long> userBooks = user.getBookList();
        userBooks.remove(bookId);
        user.setBookList(userBooks);
        userService.save(user);

        book.setIsAvailable(true);
        libraryService.save(book);
        return "Book returned successfully";
    }

    public String returnAllBooks(long userId) {
        User user = getUser(userId);

        if (user == null)
            return USER_NOT_AVAILABLE;

        List<Long> userBooks = user.getBookList();
        if (userBooks.isEmpty())
            return "User not having any Books";
        for (Long bookId : userBooks) {
            Book book = libraryService.findById(bookId).orElse(null);
            if (book != null) {
                book.setIsAvailable(true);
                libraryService.save(book);
            }
        }

        userBooks.clear();
        user.setBookList(userBooks);
        userService.save(user);

        return "All Book returned successfully";
    }

    private boolean isBookValidForUser(List<Long> booksList, String bookName) {

        Long count = booksList.stream()
                .filter(book -> bookName.equalsIgnoreCase(libraryService.findById(book).get().getBookTitle())).count();
        return count == 0;
    }

    private User getUser(Long userId) {
        return userService.findById(userId).orElse(null);
    }

}
