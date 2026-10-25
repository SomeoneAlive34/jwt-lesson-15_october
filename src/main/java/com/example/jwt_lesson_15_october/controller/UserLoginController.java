package com.example.jwt_lesson_15_october.controller;

import com.example.jwt_lesson_15_october.dto.LoginRequest;
import com.example.jwt_lesson_15_october.dto.LoginResponse;
import com.example.jwt_lesson_15_october.dto.RegisterRequest;
import com.example.jwt_lesson_15_october.dto.UserResponse;
import com.example.jwt_lesson_15_october.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userLoginService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return userLoginService.register(request);
    }
}
