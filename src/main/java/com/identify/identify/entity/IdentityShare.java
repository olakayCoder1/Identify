package com.identify.identify.entity;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IdentityShare {


    @Id
    @GeneratedValue
    public Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_id")
    public Identity identity;

    // @Column(unique = true)
    public List<String> emails;


    public boolean isActive;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Column(unique = true, nullable = false)
    private UUID identifier;



    // Or you can use this check in @PrePersist if you want to ensure it is generated only once
    @PrePersist
    public void generateUUID() {
        if (this.identifier == null || this.identifier.toString().equals("00000000-0000-0000-0000-000000000000")) {
            this.identifier = UUID.randomUUID();
        }
    }

    
}
