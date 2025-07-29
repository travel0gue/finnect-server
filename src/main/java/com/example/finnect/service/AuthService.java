package com.example.finnect.service;

import com.example.finnect.apiResponse.ErrorStatus;
import com.example.finnect.dto.AuthResponse;
import com.example.finnect.dto.LoginRequest;
import com.example.finnect.dto.SignupRequest;
import com.example.finnect.dto.TokenRefreshRequest;
import com.example.finnect.dto.TokenRefreshResponse;
import com.example.finnect.entity.RefreshToken;
import com.example.finnect.entity.Role;
import com.example.finnect.entity.User;
import com.example.finnect.exception.CustomException;
import com.example.finnect.repository.UserRepository;
import com.example.finnect.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorStatus._BAD_REQUEST);
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorStatus.DUPLICATED_USER);
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        
        User savedUser = userRepository.save(user);
        String accessToken = jwtUtil.generateAccessToken(savedUser);
        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(savedUser.getId());
        
        return new AuthResponse(accessToken, refreshTokenEntity.getToken(), savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }
    
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorStatus._LOGIN_FAILURE);
        }
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_FOUND));
        
        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user.getId());
        
        return new AuthResponse(accessToken, refreshTokenEntity.getToken(), user.getId(), user.getUsername(), user.getEmail());
    }
    
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtil.generateAccessToken(user);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                    return new TokenRefreshResponse(accessToken, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new CustomException(ErrorStatus._UNAUTHORIZED));
    }
    
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            refreshTokenService.deleteByUserId(user.getId());
        } else {
            throw new CustomException(ErrorStatus._UNAUTHORIZED);
        }
    }
}