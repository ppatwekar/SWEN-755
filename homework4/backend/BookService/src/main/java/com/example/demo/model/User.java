package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

public class User {

    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Set<UserRole> userRoles;

    @Getter
    @Setter
    private String hash;


    public User(String userId, String username, String password, Set<UserRole> userRoles, String hash) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
        this.hash = hash;
    }


    public boolean containsRole(UserRole userRole){
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRoles=" + userRoles+ '\'' +
                ", hash=" + hash +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

}
