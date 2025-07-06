package com.happypaws.backend.offersmanager.application.offers.directOffer;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.application.offers.OfferMapper;
import com.happypaws.backend.offersmanager.application.offers.create.OfferResponse;
import com.happypaws.backend.offersmanager.domain.offers.Offer;
import com.happypaws.backend.offersmanager.infrastructure.repositories.OfferRepository;
import com.happypaws.backend.offersmanager.infrastructure.repositories.ServiceRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DirectOfferCommandHandler {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final NotificationController notificationController;
    private final ServiceRepository serviceRepository;

    public OfferResponse handle(final DirectOfferCommand command) {
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

        notificationController.sendNotification(command.caregiverId(), offerResult);

        return offerResult;
    }
}
