package com.identify.identify.error;


import java.util.List;

public class ErrorResponse {

    private String message;
    private List<String> details;
    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }


    public ErrorResponse(String message, int status, List<String> details) {
        this.message = message;
        this.status = status;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
