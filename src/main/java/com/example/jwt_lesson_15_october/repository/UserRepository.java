package com.example.jwt_lesson_15_october.repository;

import com.example.jwt_lesson_15_october.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    @Override
    @EntityGraph(attributePaths = "roles")
    List<UserEntity> findAll();
}
