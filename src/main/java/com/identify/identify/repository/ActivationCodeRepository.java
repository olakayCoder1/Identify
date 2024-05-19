package com.identify.identify.repository;

import com.identify.identify.entity.ActivationCode;
import com.identify.identify.entity.Token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Integer>{
    
    Optional<ActivationCode> findByToken(String token);
}
