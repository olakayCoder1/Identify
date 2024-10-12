package com.identify.identify.dto.shares;

import java.util.List;
import java.util.UUID;

import com.identify.identify.dto.AnonymousIdentityResponseDTO;

public class AnonymousViewResponseDTO {
    
    private UUID identifier;
    private List<String> emails;
    private AnonymousIdentityResponseDTO identity;  // Change to IdentityResponseDTO


    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public AnonymousIdentityResponseDTO getIdentity() {
        return identity;
    }

    public void setIdentity(AnonymousIdentityResponseDTO identity) {
        this.identity = identity;
    }


    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }
}
