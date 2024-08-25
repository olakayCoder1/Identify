package com.identify.identify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.dto.NewIdentityRequest;
import com.identify.identify.entity.User;
import com.identify.identify.repository.UserRepository;
import com.identify.identify.service.IdentityService;

import jakarta.validation.Valid;

import com.identify.identify.helper.PremblyManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {

    private IdentityService identityService;

    private UserRepository repository;


    @Autowired
    private PremblyManager premblyManager;

    public IdentityController(IdentityService identityService,UserRepository repository) {
        this.identityService = identityService;
        this.repository = repository;
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/all")
    public ResponseEntity getAllIdentity(@AuthenticationPrincipal UserDetails currentUser) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(identityService.getAllIdentity(user));
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/add")
    public ResponseEntity addNewIdentity(
        @RequestBody @Valid NewIdentityRequest request,
        BindingResult bindingResult,
        @AuthenticationPrincipal UserDetails currentUser) throws Exception 
        {

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
            
            String username = currentUser.getUsername();
            System.out.println(username);
            User user = repository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User not found"));
        // // todo testing api call
        // String prembly = premblyManager.verifyPhoneNumber("08082838283", "NG");
        // System.out.println(prembly);
        return ResponseEntity.ok().body(identityService.addNewIdentity(request, user));
    }



}
