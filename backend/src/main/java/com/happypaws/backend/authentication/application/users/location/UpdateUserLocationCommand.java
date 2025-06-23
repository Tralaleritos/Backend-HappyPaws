package com.happypaws.backend.authentication.application.users.location;

public record UpdateUserLocationCommand(long userId, double latitude, double longitude) {
}
