package com.happypaws.backend.authentication.application.users.update;

public record UpdateUserCommand(
        long userId,
        String username,
        String email,
        String phoneNumber,
        String imgUrl) {
}
