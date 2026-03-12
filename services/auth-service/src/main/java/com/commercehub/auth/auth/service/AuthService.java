package com.commercehub.auth.auth.service;

import com.commercehub.auth.auth.dto.AuthDtos.*;
import com.commercehub.auth.auth.entity.RefreshToken;
import com.commercehub.auth.auth.entity.UserAccount;
import com.commercehub.auth.auth.repository.RefreshTokenRepository;
import com.commercehub.auth.auth.repository.UserAccountRepository;
import com.commercehub.auth.security.JwtService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserAccountRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private final JwtService jwtService;

  public AuthService(UserAccountRepository userRepository, RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtService = jwtService;
  }

  @Transactional
  public void register(RegisterRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) throw new IllegalArgumentException("El email ya existe");
    UserAccount user = new UserAccount();
    user.setEmail(request.email());
    user.setPasswordHash(encoder.encode(request.password()));
    user.setEmailVerified(true);
    userRepository.save(user);
  }

  @Transactional
  public AuthResponse login(LoginRequest request) {
    UserAccount user = userRepository.findByEmail(request.email()).orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
    if (!encoder.matches(request.password(), user.getPasswordHash())) throw new IllegalArgumentException("Credenciales inválidas");
    String refreshPlain = UUID.randomUUID().toString();
    RefreshToken token = new RefreshToken();
    token.setUser(user);
    token.setTokenHash(hash(refreshPlain));
    token.setExpiresAt(Instant.now().plusSeconds(60L * 60L * 24L * 15L));
    refreshTokenRepository.save(token);
    return new AuthResponse(jwtService.generateAccessToken(user.getId(), user.getEmail()), refreshPlain, "Bearer", 900);
  }

  @Transactional(readOnly = true)
  public AuthResponse refresh(RefreshRequest request) {
    RefreshToken token = refreshTokenRepository
        .findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash(request.refreshToken()), Instant.now())
        .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));
    UserAccount user = token.getUser();
    return new AuthResponse(jwtService.generateAccessToken(user.getId(), user.getEmail()), request.refreshToken(), "Bearer", 900);
  }

  @Transactional
  public void logout(String refreshToken) {
    refreshTokenRepository.findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash(refreshToken), Instant.now())
        .ifPresent(token -> token.setRevokedAt(Instant.now()));
  }

  @Transactional(readOnly = true)
  public MeResponse me(Long userId) {
    UserAccount user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return new MeResponse(user.getId(), user.getEmail(), user.getStatus().name());
  }

  private String hash(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      throw new IllegalStateException("No se pudo hashear token", e);
    }
  }
}
