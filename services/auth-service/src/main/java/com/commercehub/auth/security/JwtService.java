package com.commercehub.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final SecretKey key;

  public JwtService(@Value("${security.jwt.secret:default-default-default-default}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.repeat(3).substring(0, 32).getBytes(StandardCharsets.UTF_8));
  }

  public String generateAccessToken(String subject) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(900)))
        .claim("roles", "ROLE_CUSTOMER")
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}
