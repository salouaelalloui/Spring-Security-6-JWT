package com.postgresql.PostGresSecurity.Configuration;

import com.postgresql.PostGresSecurity.Entities.AccessToken;
import com.postgresql.PostGresSecurity.Enum.CustomException;
import com.postgresql.PostGresSecurity.Exceptions.LoginException;
import com.postgresql.PostGresSecurity.Exceptions.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private com.postgresql.PostGresSecurity.Services.JWTService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final  String jwtToken;
        final String userEmail;
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        AccessToken accessToken = jwtService.findByAccessToken(jwtToken).orElseThrow(()->new LoginException(CustomException.LOGIN_AGAIN));
        userEmail = jwtService.extractUsername(accessToken.getAccessToken());

        //if (jwtToken != null && !jwtService.isBlacklisted(jwtToken)) {
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }else{
                //return an exception the token is not valid
            }
        }
       /* }else{
            throw new RuntimeException("unauthorized ! You need to log in again ...");
        }*/

        filterChain.doFilter(request, response);
    }
}