package com.happypaws.backend.offersmanager.application.caregivers.create;

public record CreateCaregiverAvailabilityCommand(
        long caregiverId,
        String locationName,
        Double locationLatitude,
        Double locationLongitude
) {
}
