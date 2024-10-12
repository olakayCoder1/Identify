package com.identify.identify.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseUtil {

    public static ResponseEntity<ReturnResponse> successResponse(Object data, String message, HttpStatus statusCode) {
        ReturnResponse responseData = new ReturnResponse(!statusCode.isError(), message ,data);
        return new ResponseEntity<>(responseData, statusCode);
    }

    // public static <T> ResponseEntity<ReturnResponse<T>> successResponse(T data) {
    //     return successResponse(data, "Success", HttpStatus.OK);
    // }

    // public static <T> ResponseEntity<ReturnResponse<T>> successResponse(T data, String message) {
    //     return successResponse(data, message, HttpStatus.OK);
    // }
}
