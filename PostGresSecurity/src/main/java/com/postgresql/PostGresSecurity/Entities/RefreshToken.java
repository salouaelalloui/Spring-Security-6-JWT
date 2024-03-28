package com.postgresql.PostGresSecurity.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token ;
    private Date expiration;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
