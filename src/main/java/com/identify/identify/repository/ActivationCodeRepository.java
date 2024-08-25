package com.identify.identify.repository;

import com.identify.identify.entity.ActivationCode;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Integer>{
    

    boolean existsByCode(String generatedKey);

    Optional<ActivationCode> findByCode(String code);
}
