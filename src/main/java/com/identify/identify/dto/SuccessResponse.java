package com.identify.identify.dto;



public class SuccessResponse {

    private String message;
    private Object data; // This can be any type of data you want to include

    public SuccessResponse(String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
