package com.postgresql.PostGresSecurity.Repositories;

import com.postgresql.PostGresSecurity.Entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepositorty extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
}
