package com.happypaws.backend.offersmanager.application.caregivers.unavailable;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.infrastructure.repositories.CaregiverAvailabilityRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SetCaregiverUnavailabilityCommandHandler {
    private final UserRepository userRepository;
    private final CaregiverAvailabilityRepository availabilityRepository;
    private final NotificationController notificationController; // AGREGAR ESTA DEPENDENCIA

    public void handle(final SetCaregiverUnavailabilityCommand command) {
        final var caregiver = userRepository.findById(command.caregiverId());

        if (caregiver.isEmpty()) {
            throw new RuntimeException("Caregiver not found");
        }

        final var availability = availabilityRepository.findByCaregiver(caregiver.get());

        if (availability.isEmpty()) {
            throw new RuntimeException("Caregiver availability not found");
        }

        // OBTENER LA UBICACIÓN ANTES DE MARCAR COMO NO DISPONIBLE
        final var currentLocation = availability.get().getLocation();

        availability.get().unavailable();
        availabilityRepository.save(availability.get());

        // NOTIFICAR A LOS PET OWNERS CERCANOS QUE EL CAREGIVER YA NO ESTÁ DISPONIBLE
        if (currentLocation != null) {
            notificationController.notifyNearbyPetOwnersUnavailable(
                    caregiver.get().getId(),
                    currentLocation.latitude(),
                    currentLocation.longitude()
            );
        }
    }
}
