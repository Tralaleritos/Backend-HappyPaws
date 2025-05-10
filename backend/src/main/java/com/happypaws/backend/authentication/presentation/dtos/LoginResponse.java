package com.happypaws.backend.authentication.presentation.dtos;

public record LoginResponse(String token, long expiresIn) {
}
