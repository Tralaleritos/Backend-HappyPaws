package com.happypaws.backend.authentication.presentation.dtos;

public record UserResponse(
        long id,
        String username,
        String imgUrl
) {
}
