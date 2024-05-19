package com.identify.identify.error;

import org.springframework.http.HttpStatus;

public class ApiExeption extends RuntimeException {
    
    private final String message;
    private final HttpStatus httpStatus;

    public ApiExeption(
        String message,
        HttpStatus httpStatus
        ){
            this.message = message;
            this.httpStatus = httpStatus;
        }

    public String getMessage() {
        return message;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
