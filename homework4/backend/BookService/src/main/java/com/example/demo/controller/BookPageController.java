package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import com.example.demo.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookPageController {

    private SessionService sessionService;
    private UserDAO userDAO;

    public BookPageController(SessionService sessionService, UserDAO userDAO) {
        this.sessionService = sessionService;
        this.userDAO = userDAO;
    }

    @GetMapping("/{sessionId}")
    public String getBooksPage(@RequestParam String sessionId, Model model){

        String userId = this.sessionService.getUserId(sessionId);
        if(userId != null){
            return "inventory";
        }

        return "index";

    }

}
