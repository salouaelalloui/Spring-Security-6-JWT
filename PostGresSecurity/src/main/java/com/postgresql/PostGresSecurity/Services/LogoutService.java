package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.JwtResponseDTO;
import com.postgresql.PostGresSecurity.Entities.AccessToken;
import com.postgresql.PostGresSecurity.Entities.RefreshToken;
import com.postgresql.PostGresSecurity.Enum.CustomException;
import com.postgresql.PostGresSecurity.Exceptions.TokenException;
import com.postgresql.PostGresSecurity.Repositories.AccessTokenRepository;
import com.postgresql.PostGresSecurity.Repositories.RefreshTokenRepositorty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService {


    private final AccessTokenRepository accessTokenRepository;


    private final RefreshTokenRepositorty refreshTokenRepository;


    public void logout(JwtResponseDTO tokens) {

        AccessToken accessTokenEntity = accessTokenRepository.findByAccessToken(tokens.getAccessToken()).orElseThrow(()-> new TokenException(CustomException.TOKEN_NOT_FOUND));
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(tokens.getToken()).orElseThrow(()-> new TokenException(CustomException.TOKEN_NOT_FOUND));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( accessTokenEntity != null && refreshTokenEntity != null && authentication!= null ){
            accessTokenRepository.delete(accessTokenEntity);
            refreshTokenRepository.delete(refreshTokenEntity);
            SecurityContextHolder.clearContext();
        }


    }

}
