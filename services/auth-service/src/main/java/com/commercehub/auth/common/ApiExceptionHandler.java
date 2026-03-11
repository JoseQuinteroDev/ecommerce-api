package com.commercehub.auth.common;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException ex, HttpServletRequest req) {
    return build(HttpStatus.UNPROCESSABLE_ENTITY, "BUSINESS_RULE", ex.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", ex.getBindingResult().getFieldError().getDefaultMessage(), req.getRequestURI());
  }

  private ResponseEntity<Map<String, Object>> build(HttpStatus status, String code, String message, String path) {
    return ResponseEntity.status(status).body(Map.of(
        "timestamp", Instant.now().toString(),
        "service", "auth-service",
        "path", path,
        "code", code,
        "message", message,
        "traceId", "N/A"));
  }
}
