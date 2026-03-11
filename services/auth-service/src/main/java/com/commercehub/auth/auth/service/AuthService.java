package com.commercehub.auth.auth.service;

import com.commercehub.auth.auth.dto.AuthDtos.*;
import com.commercehub.auth.security.JwtService;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final Map<String, String> users = new ConcurrentHashMap<>();
  private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private final JwtService jwtService;

  public AuthService(JwtService jwtService) { this.jwtService = jwtService; }

  public void register(RegisterRequest request) {
    users.putIfAbsent(request.email(), encoder.encode(request.password()));
  }

  public AuthResponse login(LoginRequest request) {
    String hash = users.get(request.email());
    if (hash == null || !encoder.matches(request.password(), hash)) throw new IllegalArgumentException("Credenciales inválidas");
    String access = jwtService.generateAccessToken(request.email());
    String refresh = UUID.randomUUID().toString();
    refreshTokens.put(refresh, request.email());
    return new AuthResponse(access, refresh, "Bearer", 900);
  }

  public AuthResponse refresh(RefreshRequest request) {
    String email = refreshTokens.get(request.refreshToken());
    if (email == null) throw new IllegalArgumentException("Refresh token inválido");
    return new AuthResponse(jwtService.generateAccessToken(email), request.refreshToken(), "Bearer", 900);
  }

  public void logout(String refreshToken) { refreshTokens.remove(refreshToken); }
}
