package com.identify.identify.error;
public class ErrorResponse {

    private String message;
    private String details;
    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.details = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return message;
    }

    public void setDetails(String message) {
        this.details = message;
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
