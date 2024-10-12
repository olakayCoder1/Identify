package com.identify.identify.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;

import com.identify.identify.entity.IdentityShare;
import com.identify.identify.entity.User;


public interface IdentityShareRepository extends JpaRepository<IdentityShare, Integer> {
    
    List<IdentityShare> findByUser(User user);

    Optional<IdentityShare> findByIdentifier(UUID identifier);
}
