package com.happypaws.backend.authentication.presentation.dtos;

public record CaregiversNearbyResponse(
        long id,
        String username,
        String imgUrl,
        double latitude,
        double longitude
) {
}
