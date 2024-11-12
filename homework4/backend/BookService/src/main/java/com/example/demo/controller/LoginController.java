package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;
import com.example.demo.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private SessionService sessionService;

    private UserDAO userDAO;

    public LoginController(SessionService sessionService, UserDAO userDAO){
        this.sessionService = sessionService;
        this.userDAO = userDAO;
    }

    @PostMapping("/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        if(true){
            return ResponseEntity.ok(new LoginResponse(""));
        }
        return null;
    }

}
