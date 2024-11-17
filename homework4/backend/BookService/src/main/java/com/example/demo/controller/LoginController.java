package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;
import com.example.demo.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {

        String hash = SessionService.secureHashCreation(loginRequest.getUsername(), loginRequest.getPassword());
        if (userDAO.authenticate(hash))
        {
            String session = sessionService.createSession(userDAO.getUserId(hash));
            return ResponseEntity.ok(new LoginResponse(session));
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
