package com.example;
import com.example.UserRole;

public class Account {
    public String username;
    public String password;
    public UserRole role;

    public Account(){
        role = UserRole.ADMIN;
    }

    public Account(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.role = userRole;
    }

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

    public UserRole getUserRole() {
        return role;
    }

    public void setUserRole(UserRole userRole) {
        this.role = userRole;
    }

}
