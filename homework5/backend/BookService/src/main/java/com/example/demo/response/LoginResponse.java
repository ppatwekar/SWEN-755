package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {

    public LoginResponse() {
    }

    @Getter @Setter
    private String sessionId;

    public LoginResponse(String sessionId) {
        this.sessionId = sessionId;
    }
}
