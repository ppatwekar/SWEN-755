package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {


    private Map<String,String> sessionIdUserIdMap;

    public SessionService() {
        sessionIdUserIdMap = new HashMap<>();
    }

    public String createSession(String userId)
    {
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        sessionIdUserIdMap.put(sessionId, userId);
        return sessionId;
    }
    public String getUserId(String session){
        return "";
    }
}
