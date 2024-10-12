package com.identify.identify.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identify.identify.service.IdentitySharesService;



@RestController
@RequestMapping("/api/v1/view")
public class ViewSharesController {

    private IdentitySharesService identitySharesService;



    public ViewSharesController(IdentitySharesService identitySharesService) {
        this.identitySharesService = identitySharesService;
    }
    

     @SuppressWarnings("rawtypes")
    @GetMapping("/{identifier}")
    public ResponseEntity viewShareShareIdentity(
        @PathVariable(value="identifier") UUID identifier
    ) {
        return ResponseEntity.ok().body(identitySharesService.viewShareLink(identifier));
    }

}
