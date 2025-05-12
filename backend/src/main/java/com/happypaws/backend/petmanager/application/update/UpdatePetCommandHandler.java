package com.happypaws.backend.petmanager.application.update;

import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePetCommandHandler {
    private final PetRepository petRepository;

    public void handle(final UpdatePetCommand command) {
        final var pet = petRepository.findById(command.request().id());

        if (pet.isEmpty()){
            throw new RuntimeException("Pet not found");
        }

        pet.get().Update(
                command.request().name(),
                command.request().description(),
                command.request().species(),
                command.request().breed(),
                command.request().age(),
                command.request().imgUrl()
        );

        petRepository.save(pet.get());
    }
}
