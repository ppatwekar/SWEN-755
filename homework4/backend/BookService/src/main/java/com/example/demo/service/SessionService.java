package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    private Map<String,String> sessionIdUserIdMap;

    public SessionService() {
        sessionIdUserIdMap = new HashMap<>();
    }

    public boolean createSession(String userId){
        return false;
    }

    public String getUserId(String session){
        return "";
    }
}
