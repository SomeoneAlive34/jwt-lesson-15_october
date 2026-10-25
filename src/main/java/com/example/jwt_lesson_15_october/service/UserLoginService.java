package com.example.jwt_lesson_15_october.service;

import com.example.jwt_lesson_15_october.dto.LoginRequest;
import com.example.jwt_lesson_15_october.dto.LoginResponse;
import com.example.jwt_lesson_15_october.dto.RegisterRequest;
import com.example.jwt_lesson_15_october.dto.UserResponse;
import com.example.jwt_lesson_15_october.entity.Role;
import com.example.jwt_lesson_15_october.entity.RoleName;
import com.example.jwt_lesson_15_october.entity.UserEntity;
import com.example.jwt_lesson_15_october.exception.UsernameTakenException;
import com.example.jwt_lesson_15_october.repository.RoleRepository;
import com.example.jwt_lesson_15_october.repository.UserRepository;
import com.example.jwt_lesson_15_october.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new LoginResponse(token, "Bearer", jwtUtil.getExpirationMs(), userDetails.getUsername(), roles);
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameTakenException(request.username());
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("ROLE_USER missing. Did DataInitializer run?"));

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        return UserResponse.from(userRepository.save(user));
    }
}
