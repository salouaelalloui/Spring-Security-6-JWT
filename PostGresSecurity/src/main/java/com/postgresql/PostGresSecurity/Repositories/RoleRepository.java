package com.postgresql.PostGresSecurity.Repositories;

import com.postgresql.PostGresSecurity.Entities.Role;
import com.postgresql.PostGresSecurity.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value="select * from role where role= ?1", nativeQuery = true)
    Optional<Role> findByRole(String role);
}
