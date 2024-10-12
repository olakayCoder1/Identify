package com.identify.identify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.identify.identify.entity.Identity;
import com.identify.identify.entity.User;
import java.util.Optional;

public interface IdentityRepository extends JpaRepository<Identity, Integer> {

    List<Identity> findByUser(User user);

    Optional<Identity> findById(Integer id);

}