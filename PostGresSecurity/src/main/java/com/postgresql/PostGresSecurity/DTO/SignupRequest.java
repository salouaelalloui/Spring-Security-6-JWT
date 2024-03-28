package com.postgresql.PostGresSecurity.DTO;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    private String email;
    private String password ;
    private List<String> roles;
}
