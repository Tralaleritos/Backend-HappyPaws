package com.happypaws.backend.offersmanager.presentation.caregivers;

public record CreateCaregiverAvailabilityRequest(
        long caregiverId,
        String locationName,
        Double locationLatitude,
        Double locationLongitude
) {
}
