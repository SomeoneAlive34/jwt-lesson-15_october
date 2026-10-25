package com.example.jwt_lesson_15_october.dto;

import java.util.List;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresInMs,
        String username,
        List<String> roles
) {}
