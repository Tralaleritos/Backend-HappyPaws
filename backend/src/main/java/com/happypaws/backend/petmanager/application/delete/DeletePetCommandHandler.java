package com.happypaws.backend.petmanager.application.delete;

import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePetCommandHandler {
    private final PetRepository petRepository;

    public void handle(final DeletePetCommand command) {
        final var pet = petRepository.findById(command.id());

        if (pet.isEmpty()) {
            throw new RuntimeException("Pet not found");
        }

        petRepository.delete(pet.get());
    }
}
