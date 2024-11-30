package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {


    private Map<String,String> sessionIdUserIdMap;
    private Map<String, Date> sessionIdExpiryMap;

    private Map<String,String> userIdSessionIdMap;

    @Value("${session.defaultExpiryTime}")
    private int defaultExpiryTime;
    public SessionService() {
        sessionIdUserIdMap = new HashMap<>();
        sessionIdExpiryMap = new HashMap<>();
        userIdSessionIdMap = new HashMap<>();
    }



    public String createSession(String userId)
    {

        String preExistingSessionId = userIdSessionIdMap.get(userId);
        if(preExistingSessionId != null && checkSessionValidity(preExistingSessionId)){
            return preExistingSessionId;
        }

        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        sessionIdUserIdMap.put(sessionId, userId);
        sessionIdExpiryMap.put(sessionId, new Date(System.currentTimeMillis() + defaultExpiryTime));
        userIdSessionIdMap.put(userId,sessionId);
        return sessionId;
    }

    public boolean checkSessionValidity(String sessionId){
        if(sessionIdExpiryMap.get(sessionId) == null){
            return false;
        }
        if(sessionIdExpiryMap.get(sessionId).before(new Date(System.currentTimeMillis()))){
            System.out.println("Session "+sessionId+" is expired");
            sessionIdExpiryMap.remove(sessionId);
            userIdSessionIdMap.remove(sessionIdUserIdMap.remove(sessionId));
            return false;
        }
        return true;
    }

    public boolean logout(String sessionId){
        if(sessionIdUserIdMap.containsKey(sessionId)) {
            userIdSessionIdMap.remove(sessionIdUserIdMap.remove(sessionId));
            sessionIdExpiryMap.remove(sessionId);
            return true;
        }
        return false;
    }

    public String getUserId(String session)
    {
        return sessionIdUserIdMap.get(session);
    }

    public static final String secureHashCreation(String userInput, String passwordInput) throws NoSuchAlgorithmException {
        String combineddCredential = userInput + passwordInput;
        System.out.println(combineddCredential);

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] byteArray = md.digest(combineddCredential.getBytes(StandardCharsets.UTF_8));

        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, byteArray);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        System.out.println(hexString.toString());

        return hexString.toString();


    }
}
