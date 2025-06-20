package com.example.mimolearningapp.Model;

public class LoginRequest {
    private String Email;
    private String Password;

    public LoginRequest(String username, String password) {
        this.Email = username;
        this.Password = password;
    }
}
