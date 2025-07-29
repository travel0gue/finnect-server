package com.example.finnect.service;

import com.example.finnect.apiResponse.ErrorStatus;
import com.example.finnect.entity.RefreshToken;
import com.example.finnect.entity.User;
import com.example.finnect.exception.CustomException;
import com.example.finnect.repository.RefreshTokenRepository;
import com.example.finnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    @Value("${jwt.refreshExpiration}")
    private Long refreshTokenDurationMs;
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_FOUND));
        
        // 기존 refresh token이 있다면 삭제
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);
        
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        
        return refreshTokenRepository.save(refreshToken);
    }
    
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new CustomException(ErrorStatus._UNAUTHORIZED);
        }
        return token;
    }
    
    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_FOUND));
        refreshTokenRepository.deleteByUser(user);
    }
}