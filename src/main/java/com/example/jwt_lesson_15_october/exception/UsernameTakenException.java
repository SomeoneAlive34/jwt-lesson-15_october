package com.example.jwt_lesson_15_october.exception;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super("Username already taken: " + username);
    }
}
