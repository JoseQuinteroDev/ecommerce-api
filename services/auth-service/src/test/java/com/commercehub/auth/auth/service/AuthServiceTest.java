package com.commercehub.auth.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.commercehub.auth.auth.dto.AuthDtos.LoginRequest;
import com.commercehub.auth.auth.dto.AuthDtos.RegisterRequest;
import com.commercehub.auth.auth.entity.UserAccount;
import com.commercehub.auth.auth.repository.RefreshTokenRepository;
import com.commercehub.auth.auth.repository.UserAccountRepository;
import com.commercehub.auth.security.JwtService;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AuthServiceTest {
  @Test
  void registerThrowsWhenEmailAlreadyExists() {
    UserAccountRepository userRepo = mock(UserAccountRepository.class);
    RefreshTokenRepository tokenRepo = mock(RefreshTokenRepository.class);
    JwtService jwtService = mock(JwtService.class);
    AuthService service = new AuthService(userRepo, tokenRepo, jwtService);

    when(userRepo.findByEmail("user@mail.com")).thenReturn(Optional.of(new UserAccount()));

    assertThrows(IllegalArgumentException.class, () -> service.register(new RegisterRequest("user@mail.com", "Pass123!")));
  }

  @Test
  void loginThrowsForUnknownUser() {
    UserAccountRepository userRepo = mock(UserAccountRepository.class);
    RefreshTokenRepository tokenRepo = mock(RefreshTokenRepository.class);
    JwtService jwtService = mock(JwtService.class);
    AuthService service = new AuthService(userRepo, tokenRepo, jwtService);

    when(userRepo.findByEmail("missing@mail.com")).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> service.login(new LoginRequest("missing@mail.com", "x")));
  }
}
