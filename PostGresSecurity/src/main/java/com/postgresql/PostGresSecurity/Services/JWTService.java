package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.JwtResponseDTO;
import com.postgresql.PostGresSecurity.Entities.AccessToken;
import com.postgresql.PostGresSecurity.Entities.RefreshToken;
import com.postgresql.PostGresSecurity.Entities.User;
import com.postgresql.PostGresSecurity.Repositories.AccessTokenRepository;
import com.postgresql.PostGresSecurity.Repositories.RefreshTokenRepositorty;
import com.postgresql.PostGresSecurity.Repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTService {

    private SecretKey Key;
    private  static  final long EXPIRATION_TIME_ATOKEN = 3600000;
    private  static  final long EXPIRATION_TIME_REFRESHTOKEN = 36000000; //24hours or 86400000 milisecs
@Autowired
    private   AccessTokenRepository accessTokenRepository;
@Autowired
    private  RefreshTokenRepositorty refreshTokenRepositorty;
@Autowired
    private  UserRepository userRepository;
   // private Set<String> blacklist = new HashSet<>();

    public JWTService(){

        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }
    /*public void addToBlacklist(JwtResponseDTO token) {
        blacklist.add(token.getAccessToken());
        blacklist.add(token.getToken());
    }


    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }*/
   public String generateToken(String user){
       Map<String, Object> claims = new HashMap<>();
       return createToken(claims, user);
   }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("there is no user by this email"));
    }
   private String createToken (Map<String, Object> claims, String username) {

       System.out.println("username: "+username);
       Date date= new Date(System.currentTimeMillis() + EXPIRATION_TIME_ATOKEN);
        User user= userRepository.findByEmail(username).orElseThrow();
       System.out.println("username after: "+user.getEmail());
       String accessToken= Jwts.builder()
               .subject(username)
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(date)
               .signWith(Key)
               .compact();
       AccessToken tokenEntity = new AccessToken();
       tokenEntity.setAccessToken(accessToken);
       tokenEntity.setExpiration(date);
       tokenEntity.setUser(user);
       accessTokenRepository.save(tokenEntity);
       return accessToken;
   }


    public String createRefreshToken(String username){
        User user = userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("th"));

        Date date= new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESHTOKEN);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiration(date) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
         refreshTokenRepositorty.save(refreshToken);
         return refreshToken.getToken();
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepositorty.findByToken(token);
    }
    public Optional<AccessToken> findByAccessToken(String token){
        return accessTokenRepository.findByAccessToken(token);
    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiration().compareTo(new Date(System.currentTimeMillis()))<0){
            refreshTokenRepositorty.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }


    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
 //   public boolean isRefreshTokenExpired(String token){
   //     return extractClaims(token, Claims::getExpiration).before(new Date());
    //}

}