package com.happypaws.backend.offersmanager.application.caregivers.unavailable;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.infrastructure.CaregiverAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SetCaregiverUnavailabilityCommandHandler {
    private final UserRepository userRepository;
    private final CaregiverAvailabilityRepository availabilityRepository;

    public void handle(final SetCaregiverUnavailabilityCommand command) {
        final var caregiver = userRepository.findById(command.caregiverId());

        if (caregiver.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        final var availability = availabilityRepository.findByCaregiver(caregiver.get());

        if (availability.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        availability.get().unavailable();
        availabilityRepository.save(availability.get());
    }
}
