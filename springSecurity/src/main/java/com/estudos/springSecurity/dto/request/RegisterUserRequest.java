package com.estudos.springSecurity.dto.request;

public record RegisterUserRequest(
        String name,
        String email,
        String password
) {
}
