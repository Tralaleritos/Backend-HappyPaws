package com.happypaws.backend.offersmanager.application.offers.create;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.offersmanager.application.offers.OfferMapper;
import com.happypaws.backend.offersmanager.domain.offers.Offer;
import com.happypaws.backend.offersmanager.infrastructure.OfferRepository;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateOfferCommandHandler {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public OfferResponse handle(final CreateOfferCommand command) {
        final var owner = userRepository.findById(command.ownerId());

        if (owner.isEmpty()) {
            throw new RuntimeException("Owner not found");
        }

        final var pets = petRepository.findAllById(command.pets());

        if (pets.isEmpty()) {
            throw new RuntimeException("Pets not found");
        }

        final var offer = Offer.Create(
                command.location(),
                command.description(),
                command.range(),
                owner.get(),
                pets
        );

        final var offerSaved = offerRepository.save(offer);

        return OfferMapper.fromEntity(offerSaved);
    }
}
