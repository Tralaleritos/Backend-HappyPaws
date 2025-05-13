package com.happypaws.backend.offersmanager.application.caregivers.availability;

public record SetCaregiverAvailabilityCommand(
        long caregiverId,
        String locationName,
        Double locationLatitude,
        Double locationLongitude
) {
}
