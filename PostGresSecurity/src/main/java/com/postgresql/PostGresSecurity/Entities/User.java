package com.postgresql.PostGresSecurity.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToMany(targetEntity=AccessToken.class,mappedBy="user",fetch=FetchType.LAZY)
    @JsonIgnore
    private List<AccessToken> accessTokens;
    @OneToMany(targetEntity=RefreshToken.class,mappedBy="user",fetch=FetchType.LAZY)
    @JsonIgnore
    private List<RefreshToken> refreshTokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}



