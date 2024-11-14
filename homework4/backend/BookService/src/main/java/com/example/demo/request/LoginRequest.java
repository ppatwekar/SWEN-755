package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;
import com.example.demo.dao.UserDAO;

import java.security.NoSuchAlgorithmException;

public class LoginRequest {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String secureHash;

    public LoginRequest(String username, String password) throws NoSuchAlgorithmException
    {
        this.secureHash = UserDAO.secureHashCreation(username, password);
        this.username = username;
        this.password = password;
    }
}
