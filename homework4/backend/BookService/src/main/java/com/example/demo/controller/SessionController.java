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

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    private UserDAO userDAO;

    public SessionController(SessionService sessionService, UserDAO userDAO){
        this.sessionService = sessionService;
        this.userDAO = userDAO;
    }

    @PostMapping("/")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {

        String hash =  SessionService.secureHashCreation(loginRequest.getUsername(), loginRequest.getPassword());


        if(true){
            return ResponseEntity.ok(new LoginResponse(""));
        }
        return null;
    }

}
