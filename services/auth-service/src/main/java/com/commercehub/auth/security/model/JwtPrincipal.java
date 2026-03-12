package com.commercehub.auth.security.model;

public record JwtPrincipal(Long userId, String email) {}
