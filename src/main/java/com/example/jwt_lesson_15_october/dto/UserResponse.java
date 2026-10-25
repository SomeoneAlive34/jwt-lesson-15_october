package com.example.jwt_lesson_15_october.dto;

import com.example.jwt_lesson_15_october.entity.UserEntity;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        boolean enabled,
        List<String> roles
) {
    public static UserResponse from(UserEntity user) {
        List<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .sorted()
                .toList();
        return new UserResponse(user.getId(), user.getUsername(), user.isEnabled(), roleNames);
    }
}
