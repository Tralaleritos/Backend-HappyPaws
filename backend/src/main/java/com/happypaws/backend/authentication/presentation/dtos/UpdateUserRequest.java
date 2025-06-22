package com.happypaws.backend.authentication.presentation.dtos;

public record UpdateUserRequest(
        String username,
        String email,
        String phoneNumber,
        String imgUrl
) {
}
