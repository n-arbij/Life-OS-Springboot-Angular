package com.barak.lifeOS.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.barak.lifeOS.exception.ResourceNotFoundException;
import com.barak.lifeOS.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository tokenRepository;

    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry;

   @Transactional
    public RefreshToken create(User user) {
        RefreshToken token = tokenRepository.findByUser(user)
            .orElse(new RefreshToken());

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(Instant.now().plusMillis(refreshTokenExpiry));
        token.setRevoked(false);

        return tokenRepository.save(token);
    }

    public RefreshToken validate(String tokenString){
        RefreshToken token = tokenRepository.findByToken(tokenString)
            .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
        
        if(!token.isValid()) {
            throw new IllegalArgumentException(
                "Refresh token has expired or been revoked. Please log in again."
            );
        }

        return token;
    }

    public RefreshToken rotate(RefreshToken oldToken){
        oldToken.setRevoked(true);
        tokenRepository.save(oldToken);
        return create(oldToken.getUser());
    }

    public void revokeAll(User user){
        tokenRepository.deleteByUser(user);
    }
}
