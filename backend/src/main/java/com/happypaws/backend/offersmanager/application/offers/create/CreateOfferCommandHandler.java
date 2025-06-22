package com.happypaws.backend.offersmanager.application.offers.create;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.application.offers.OfferMapper;
import com.happypaws.backend.offersmanager.domain.offers.Offer;
import com.happypaws.backend.offersmanager.infrastructure.repositories.OfferRepository;
import com.happypaws.backend.offersmanager.infrastructure.repositories.ServiceRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CreateOfferCommandHandler {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final NotificationController notificationController;
    private final AvailableCaregivers availableCaregivers;
    private final ServiceRepository serviceRepository;

    public OfferResponse handle(final CreateOfferCommand command) {
        final var owner = userRepository.findById(command.ownerId());

        if (owner.isEmpty()) {
            throw new RuntimeException("Owner not found");
        }

        final var pets = petRepository.findAllById(command.pets());

        if (pets.isEmpty()) {
            throw new RuntimeException("Pets not found");
        }

        final var services = serviceRepository.findAllById(command.services());

        if (services.isEmpty()) {
            throw new RuntimeException("Services not found");
        }

        final var offer = Offer.Create(
                command.location(),
                command.description(),
                command.range(),
                owner.get(),
                pets,
                command.price(),
                services
        );

        final var offerSaved = offerRepository.save(offer);

        final var offerResult = OfferMapper.fromEntity(offerSaved);

        final List<Long> caregiverIds = availableCaregivers.getAvailableCaregivers(offerSaved.getLocation());

        log.info("Caregiver ids: {}", caregiverIds);

        for (final var caregiverId : caregiverIds) {
            notificationController.sendNotification(caregiverId, offerResult);
        }

        return offerResult;
    }
}
