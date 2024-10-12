package com.identify.identify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.dto.IdentityResponseDTO;
import com.identify.identify.dto.IdentityUpdateDto;
import com.identify.identify.dto.NewIdentityRequest;
import com.identify.identify.dto.ResponseUtil;
import com.identify.identify.dto.auth.AccountAtivationRequest;
import com.identify.identify.dto.shares.ShareIdentityRequest;
import com.identify.identify.entity.User;
import com.identify.identify.error.ApiRequestException;
import com.identify.identify.error.ErrorResponse;
import com.identify.identify.repository.UserRepository;
import com.identify.identify.service.IdentityService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {

    private IdentityService identityService;

    private UserRepository repository;


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
            User user = repository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User not found"));
                String resultMessage = (String) identityService.addNewIdentity(request, user);
                return ResponseUtil.successResponse(null, resultMessage, HttpStatus.OK);
    }


    @GetMapping("/{identityID}")
    public ResponseEntity getIndentityById(@PathVariable(value="identityID") Integer identityID){
        return ResponseEntity.ok().body(identityService.getSingleIdentity(identityID));
    }


    @PutMapping("/{identityID}")
    public ResponseEntity updateIdentityById(
        @PathVariable(value = "identityID") Integer identityID,
        @RequestBody @Valid IdentityUpdateDto request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            // Convert errors to a more readable format
            Map<String, Object> errors = new HashMap<>();
            errors.put("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            error -> error.getField(),
                            error -> error.getDefaultMessage()
                    )));

        return ResponseUtil.successResponse(errors, "Bad request", HttpStatus.BAD_REQUEST);
        // return ResponseEntity.badRequest().body(errors);
            // return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Calling the service to update identity status
            IdentityResponseDTO updatedIdentity = identityService.updateSingleIdentity(identityID, request.getActive());

            // Returning the response with status OK and the updated Identity
            return ResponseEntity.ok(updatedIdentity);
        } catch (ApiRequestException ex) {
            return ResponseEntity.ok().body("");
        }
    }


    
}
