package com.example.finnect.controller;

import com.example.finnect.apiResponse.ApiResponse;
import com.example.finnect.dto.AuthResponse;
import com.example.finnect.dto.LoginRequest;
import com.example.finnect.dto.SignupRequest;
import com.example.finnect.dto.TokenRefreshRequest;
import com.example.finnect.dto.TokenRefreshResponse;
import com.example.finnect.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입, 로그인 API")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }
    
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 인증 후 JWT 토큰을 발급합니다.")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }
    
    @GetMapping("/me")
    @Operation(summary = "현재 사용자 정보", description = "인증된 사용자의 정보를 조회합니다.")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<String>> getCurrentUser() {
        return ResponseEntity.ok(ApiResponse.onSuccess("인증된 사용자입니다."));
    }
    
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자를 로그아웃하고 리프레시 토큰을 무효화합니다.")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<String>> logout() {
        authService.logout();
        return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃되었습니다."));
    }
}