package com.happypaws.backend.offersmanager.presentation.caregivers;

public record SetCaregiverAvailabilityRequest(
        String locationName,
        Double locationLatitude,
        Double locationLongitude
) {
}
