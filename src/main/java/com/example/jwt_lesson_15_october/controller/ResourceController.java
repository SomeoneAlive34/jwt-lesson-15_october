package com.example.jwt_lesson_15_october.controller;

import com.example.jwt_lesson_15_october.dto.UserResponse;
import com.example.jwt_lesson_15_october.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResourceController {

    private final UserService userService;

    @GetMapping("/public/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Public endpoint no token needed");
    }

    @GetMapping("/user/me")
    public UserResponse me(Authentication authentication) {
        return userService.getByUsername(authentication.getName());
    }

    @GetMapping("/admin/users")
    public List<UserResponse> allUsers() {
        return userService.getAll();
    }

    @GetMapping("/admin/stats")
    public Map<String, Long> getStats() {
        return Map.of("totalUsers", userService.getUserCount());
    }
}