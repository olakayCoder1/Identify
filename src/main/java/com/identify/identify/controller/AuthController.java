package com.identify.identify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.dto.AuthenticationRequest;
import com.identify.identify.dto.AuthenticationResponse;
import com.identify.identify.dto.RegisterRequest;
import com.identify.identify.helper.mail.EmailSender;
import com.identify.identify.service.AuthService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authlogin(
        @RequestBody AuthenticationRequest request) {

        
        return ResponseEntity.ok(authService.authlogin(request));
    }


    
}