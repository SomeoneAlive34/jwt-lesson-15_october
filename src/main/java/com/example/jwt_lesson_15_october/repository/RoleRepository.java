package com.example.jwt_lesson_15_october.repository;

import com.example.jwt_lesson_15_october.entity.Role;
import com.example.jwt_lesson_15_october.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
