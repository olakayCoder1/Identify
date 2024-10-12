package com.identify.identify.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.dto.shares.IdentityShareUpdateRequest;
import com.identify.identify.dto.shares.ShareIdentityRequest;
import com.identify.identify.entity.User;
import com.identify.identify.repository.UserRepository;
import com.identify.identify.service.IdentitySharesService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/shares")
public class IdentitySharesController {


    private IdentitySharesService identitySharesService;

    private UserRepository repository;


    public IdentitySharesController(IdentitySharesService identitySharesService,UserRepository repository) {
        this.identitySharesService = identitySharesService;
        this.repository = repository;
    }


    @SuppressWarnings("rawtypes")
    @GetMapping("")
    public ResponseEntity getAllSharesIdentity(@AuthenticationPrincipal UserDetails currentUser) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(identitySharesService.getAllShares(user));
    }


    @SuppressWarnings("rawtypes")
    @PostMapping("")
    public ResponseEntity createNewShareIdentity(
        @RequestBody @Valid ShareIdentityRequest request,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(identitySharesService.createNewShareLink(user, request.getIndentityId(),request.getEmails()));
    }


    @SuppressWarnings("rawtypes")
    @GetMapping("/{shareIdentityID}")
    public ResponseEntity getSingleShareIdentity(
        @PathVariable(value="shareIdentityID") Integer identityID,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(identitySharesService.getSingleShareLink(user, identityID));
    }


    @SuppressWarnings("rawtypes")
    @PutMapping("/{shareIdentityID}")
    public ResponseEntity updateSingleShareIdentity(
        @PathVariable(value="shareIdentityID") Integer identityID,
        @RequestBody @Valid IdentityShareUpdateRequest request,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(identitySharesService.updateSingleShareLink(user, identityID, request.getStatus()));
    }


    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{shareIdentityID}")
    public ResponseEntity deleteSingleShareIdentity(
        @PathVariable(value="shareIdentityID") Integer identityID,
        @AuthenticationPrincipal UserDetails currentUser
    ) {
        String username = currentUser.getUsername();
        System.out.println(username);
        User user = repository.findByEmail(username).orElseThrow(
            () -> new RuntimeException("User not found"));

        identitySharesService.deleteSingleShareLink(user, identityID);
        return ResponseEntity.noContent().build();
    }


   
    
}
