package com.postgresql.PostGresSecurity.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken ;
    private Date expiration;
    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

}
