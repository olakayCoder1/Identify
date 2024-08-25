package com.identify.identify.dto.auth;

import com.identify.identify.entity.User;

public class RegisterResponse {

    private String message;
    private User data;

    public RegisterResponse(String message, User data) {
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
