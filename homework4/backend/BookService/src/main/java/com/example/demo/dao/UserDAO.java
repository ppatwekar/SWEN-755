package com.example.demo.dao;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {

    private ObjectMapper objectMapper;
    private ResourceLoader resourceLoader;

    private final String STORAGE_FILE = "classpath:users.json";

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private Map<String,User> userMap;

    public UserDAO(ObjectMapper objectMapper,ResourceLoader resourceLoader) throws IOException {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.initUserMap();
    }

    public boolean authenticate(String username, String password)
    {
        for (User user : userMap.values())
        {
            {
                if (user.getPassword().equals(password))
                {
                    if (user.getUsername().equals(username))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getUserId(String hash)
    {
        for (User user : userMap.values())
        {
            {
                if (user.getHash().equals(hash))
                {
                    return user.getUserId();
                }
            }
        }
        return null;
    }

    public boolean isAuthorizedToViewAdminContent(String userId)
    {
        if(userMap.containsKey(userId))
        {
            for(UserRole role  : userMap.get(userId).getUserRoles())
            {
                if(role.equals(UserRole.ADMIN))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isAuthorizedToViewCustomerContent(String userId)
    {
        if (userMap.containsKey(userId)) {
            for (UserRole role : userMap.get(userId).getUserRoles())
            {
                if (role.equals(UserRole.CUSTOMER)) {
                    return true;
                }
            }
        }
        return false;
    }
    public User getUser(String userId)
    {
        return userMap.get(userId);
    }

    public boolean secureHashValidation(String hexString)
    {
        for (User user : userMap.values())
        {
            if (user.getHash().equals(hexString))
            {
                return true;
            }
        }
        return false;
    }

    public User updateUser(User user){
        if(userMap.containsKey(user.getUserId())){
            userMap.put(user.getUserId(), user);
            return user;
        }
        return null;
    }

    public boolean userHasRole(String userId, UserRole userRole){
        return userMap.containsKey(userId) && userMap.get(userId).containsRole(userRole);
    }

    public void initUserMap() throws IOException {
        logger.info("Initializing userMap");
        logger.info("Reading from users.json");
        Resource resource = resourceLoader.getResource(STORAGE_FILE);

        logger.info("Reading from users.json");
        List<User> users = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<User>>() {});

        populateUserMap(users);
    }

    private void populateUserMap(List<User> users){
        this.userMap = new HashMap<>();
        logger.info("Populating user map");
        users.forEach(user -> {
            logger.info(user.toString());
            userMap.put(user.getUserId(), user);
        });
        logger.info("Done Populating user map");
    }
}
