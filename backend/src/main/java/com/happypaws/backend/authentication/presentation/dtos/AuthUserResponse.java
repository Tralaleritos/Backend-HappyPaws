package com.happypaws.backend.authentication.presentation.dtos;

public record AuthUserResponse(
         long id,
         String username,
         String email,
         String phoneNumber,
         String imgUrl
) {
}
