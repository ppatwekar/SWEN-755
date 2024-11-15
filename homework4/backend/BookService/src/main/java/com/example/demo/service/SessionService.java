package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
