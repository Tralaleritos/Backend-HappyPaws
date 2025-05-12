package com.happypaws.backend.petmanager.application.create;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.petmanager.application.PetMapper;
import com.happypaws.backend.petmanager.domain.Pet;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePetCommandHandler {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetResponse handle(final CreatePetCommand command) {
        final var owner = userRepository.findById(command.request().ownerId());

        if (owner.isEmpty()) {
            throw new RuntimeException("Owner not found");
        }

        final var pet = Pet.Create(
                command.request().name(),
                command.request().description(),
                command.request().species(),
                command.request().breed(),
                command.request().age(),
                owner.get(),
                command.request().imgUrl()
        );

        final var savedPet = petRepository.save(pet);

        return PetMapper.fromEntity(savedPet);
    }
}
