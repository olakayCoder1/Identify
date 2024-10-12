package com.identify.identify.dto.shares;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.identify.identify.dto.IdentityResponseDTO;


// Assuming Identity is another DTO or the Entity itself
public class IdentitySharesResponseDTO {

    private Integer id;
    private UUID identifier;
    private List<String> emails;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private IdentityResponseDTO identity;  // Change to IdentityResponseDTO

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public IdentityResponseDTO getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityResponseDTO identity) {
        this.identity = identity;
    }


    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }
}