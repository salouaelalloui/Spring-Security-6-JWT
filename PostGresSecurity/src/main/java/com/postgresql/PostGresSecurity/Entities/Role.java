package com.postgresql.PostGresSecurity.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postgresql.PostGresSecurity.Enum.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
    @ElementCollection(targetClass = Permission.class)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permission> permissions;
}
