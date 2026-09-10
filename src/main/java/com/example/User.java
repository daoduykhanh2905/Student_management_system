package com.example;
import com.example.UserRole;

import java.util.Scanner;

public class User {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    String username;
    String password;
    UserRole role;
    public User(){}

    public void login(){

    }

}
