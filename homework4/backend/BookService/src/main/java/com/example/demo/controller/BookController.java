package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {


    public BookController(BookService bookService, SessionService sessionService, UserDAO userDAO) {
        this.bookService = bookService;
        this.sessionService = sessionService;
        this.userDAO = userDAO;
    }

    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    private SessionService sessionService;

    private UserDAO userDAO;

    /*
    This method will later become
    getBooks(@RequestParam(required = true) String sessionId)
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<List<Book>> getBooks(@RequestParam String sessionId){
        logger.info("Received request to get all books...");

        String userId = this.sessionService.getUserId(sessionId);

        boolean isAuth = userDAO.isAuthorizedToViewAdminContent(userId);

        if(isAuth) {

            try {
                List<Book> books = bookService.getBooks();
                return new ResponseEntity<>(books, HttpStatus.OK);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
