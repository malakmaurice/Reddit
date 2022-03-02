package com.example.reddit.service;

import com.example.reddit.Entity.RefreshToken;
import com.example.reddit.Reposotory.RefreshTokenRepository;
import com.example.reddit.dto.RefreshTokenRequest;
import com.example.reddit.exception.springRadditException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {

   private final RefreshTokenRepository refreshTokenRepository;

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        this.refreshTokenRepository.deleteByRefreshToken(refreshTokenRequest.getRefreshToken());
    }

    public String generateRefreshToken() {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(token);
        refreshToken.setCeatedAt(Instant.now());
        this.refreshTokenRepository.save(refreshToken);
        return token;
    }

    void validateRefreshToken(String refreshToken){
        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new springRadditException("invalid refresh token"));
    }
}
