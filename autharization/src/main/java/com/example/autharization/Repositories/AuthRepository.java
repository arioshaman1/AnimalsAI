package com.example.autharization.Repositories;

import com.example.autharization.entities.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByUsername(String username);
    Optional<AuthEntity> findByEmail(String email);
}
