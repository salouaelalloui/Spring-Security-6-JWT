package com.postgresql.PostGresSecurity.Controllers;

import com.postgresql.PostGresSecurity.DTO.AuthRequestDTO;
import com.postgresql.PostGresSecurity.DTO.JwtResponseDTO;
import com.postgresql.PostGresSecurity.DTO.RefreshTokenRequestDTO;
import com.postgresql.PostGresSecurity.DTO.SignupRequest;
import com.postgresql.PostGresSecurity.Entities.RefreshToken;
import com.postgresql.PostGresSecurity.Entities.User;
import com.postgresql.PostGresSecurity.Services.LogoutService;
import com.postgresql.PostGresSecurity.Services.JWTService;
import com.postgresql.PostGresSecurity.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final JWTService jwtService;
    private final LogoutService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/user/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            String username = authentication.getName();
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(username))
                    .token(jwtService.createRefreshToken(username)).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }
    @PostMapping("/getUser")
    public User getUserByEmail(@RequestParam String email){
        User user = jwtService.getUserByEmail(email);
        return user;
    }

    @PostMapping("/user/refresh")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return jwtService.findByToken(refreshTokenRequestDTO.getToken())
                .map(jwtService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getEmail());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup (@RequestBody SignupRequest user) {
        User us= userService.saveUser(user);
        if(us.getId()>0){
            return  ResponseEntity.ok("the user has been saved !");

        }
        return ResponseEntity.status(400).body("error, user not saved");
    }

    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(@RequestBody JwtResponseDTO tokens) {
        //jwtService.addToBlacklist(tokens);
        authService.logout(tokens);
        return ResponseEntity.ok("Logout successful");

    }


}
