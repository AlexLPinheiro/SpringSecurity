package com.estudos.springSecurity.dto.request;

public record LoginRequest(
   String email,
   String password
) {}
