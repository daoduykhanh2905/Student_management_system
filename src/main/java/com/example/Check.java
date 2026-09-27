package com.example;
import com.example.UserRole;


public class Check {
    static Account[] acc = new Account[100];
    static int accCount = 0;
    static {
        acc[accCount++] = new Account("khanh", "1234", UserRole.ADMIN);
    }
    public Account login(String username, String password) {
        for (int i = 0; i < accCount; i++) {
            if (acc[i].getUsername().equals(username) && acc[i].getPassword().equals(password)) {
                return acc[i];
            }
        }
        return null;
    }
    public boolean register(String username, String password, UserRole role){
        for(int i=0; i<accCount; i++){
            if(acc[i].getUsername().equals(username)){
                return false;
            }
        }
        if(accCount >= acc.length){return false;}
        acc[accCount++] = new Account(username, password,role);
        accCount++;
        return true;
    }

}
