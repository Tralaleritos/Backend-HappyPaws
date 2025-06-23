package com.happypaws.backend.offersmanager.application.caregivers.availability;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import com.happypaws.backend.offersmanager.infrastructure.repositories.CaregiverAvailabilityRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SetCaregiverAvailabilityCommandHandler {
    private final CaregiverAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final NotificationController notificationController;

    public void handle(final SetCaregiverAvailabilityCommand command) {
        final var caregiver = userRepository.findById(command.caregiverId());

        if (caregiver.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        final var availability = availabilityRepository.findByCaregiver(caregiver.get());

        if (availability.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        final var location = new Location(
                command.locationName(),
                command.locationLatitude(),
                command.locationLongitude()
        );

        availability.get().setAvailable(location);
        availabilityRepository.save(availability.get());

        notificationController.notifyNearbyPetOwners(
                caregiver.get().getId(),
                availability.get().getLocation().latitude(),
                availability.get().getLocation().longitude());
    }
}
