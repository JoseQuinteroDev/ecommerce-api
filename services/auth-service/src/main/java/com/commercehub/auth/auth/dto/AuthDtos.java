package com.commercehub.auth.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
  public record RegisterRequest(@Email String email, @NotBlank String password) {}
  public record LoginRequest(@Email String email, @NotBlank String password) {}
  public record RefreshRequest(@NotBlank String refreshToken) {}
  public record AuthResponse(String accessToken, String refreshToken, String tokenType, long expiresIn) {}
  public record MeResponse(Long userId, String email, String status) {}
}
