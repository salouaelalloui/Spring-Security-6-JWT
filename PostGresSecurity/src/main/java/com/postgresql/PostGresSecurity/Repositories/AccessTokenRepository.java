package com.postgresql.PostGresSecurity.Repositories;

import com.postgresql.PostGresSecurity.Entities.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken>  findByAccessToken(String access_token);
}
