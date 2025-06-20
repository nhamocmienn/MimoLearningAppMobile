package com.example.mimolearningapp.Model;

public class UpdateProfileRequest {
    private int id;
    private String email;
    private String password;

    public UpdateProfileRequest(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}

