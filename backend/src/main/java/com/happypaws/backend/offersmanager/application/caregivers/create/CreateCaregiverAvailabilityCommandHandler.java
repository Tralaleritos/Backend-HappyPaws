package com.happypaws.backend.offersmanager.application.caregivers.create;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.domain.caregivers.CaregiverAvailability;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import com.happypaws.backend.offersmanager.infrastructure.repositories.CaregiverAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCaregiverAvailabilityCommandHandler {
    private final CaregiverAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public long handle(final CreateCaregiverAvailabilityCommand command) {
        final var caregiver = userRepository.findById(command.caregiverId());

        if (caregiver.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        final var location = new Location(
                command.locationName(),
                command.locationLatitude(),
                command.locationLongitude()
        );

        final var caregiverAvailability = CaregiverAvailability.Create(
                caregiver.get(),
                location,
                true
        );

        final var saved = availabilityRepository.save(caregiverAvailability);

        return saved.getId();
    }
}
