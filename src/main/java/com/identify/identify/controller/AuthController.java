package com.identify.identify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.dto.SuccessResponse;
import com.identify.identify.dto.auth.AccountAtivationRequest;
import com.identify.identify.dto.auth.AuthenticationRequest;
import com.identify.identify.dto.auth.AuthenticationResponse;
import com.identify.identify.dto.auth.RegisterRequest;
import com.identify.identify.dto.auth.RegisterResponse;
import com.identify.identify.service.AuthService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
                // Convert errors to a more readable format
                Map<String, Object> errors = new HashMap<>();
                errors.put("errors", bindingResult.getFieldErrors().stream()
                        .collect(Collectors.toMap(
                                error -> error.getField(),
                                error -> error.getDefaultMessage()
                        )));
                return ResponseEntity.badRequest().body(errors);
            }
            
        RegisterResponse result = authService.register(request);
        SuccessResponse successResponse = new SuccessResponse(result.getMessage(), result.getData()); // Replace null with any additional data if needed
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<Object> accountActivate(@RequestBody @Valid AccountAtivationRequest request,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
                // Convert errors to a more readable format
                Map<String, Object> errors = new HashMap<>();
                errors.put("errors", bindingResult.getFieldErrors().stream()
                        .collect(Collectors.toMap(
                                error -> error.getField(),
                                error -> error.getDefaultMessage()
                        )));
                return ResponseEntity.badRequest().body(errors);
            }
        return ResponseEntity.ok(authService.accountActivate(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authlogin(
        @RequestBody AuthenticationRequest request) {

        
        return ResponseEntity.ok(authService.authlogin(request));
    }


    
}