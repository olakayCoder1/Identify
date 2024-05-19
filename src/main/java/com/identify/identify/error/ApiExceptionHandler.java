package com.identify.identify.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler( value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExeption apiExeption = new ApiExeption(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(apiExeption,badRequest);
    }


}