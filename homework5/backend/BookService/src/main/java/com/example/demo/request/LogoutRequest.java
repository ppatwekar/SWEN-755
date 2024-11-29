package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;

public class LogoutRequest {

    @Getter @Setter
    private String sessionId;

    public LogoutRequest() {

    }

    public LogoutRequest(String sessionId) {
        this.sessionId = sessionId;
    }
}
