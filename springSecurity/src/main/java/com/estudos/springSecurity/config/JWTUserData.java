package com.estudos.springSecurity.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
