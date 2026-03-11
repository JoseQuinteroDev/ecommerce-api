package com.commercehub.auth.auth.controller;

import com.commercehub.auth.auth.dto.AuthDtos.*;
import com.commercehub.auth.auth.service.AuthService;
import com.commercehub.auth.security.model.JwtPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) { this.authService = authService; }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest request) {
    return ResponseEntity.ok(authService.refresh(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest request) {
    authService.logout(request.refreshToken());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/me")
  public ResponseEntity<MeResponse> me(@AuthenticationPrincipal JwtPrincipal principal) {
    return ResponseEntity.ok(authService.me(principal.userId()));
  }
}
