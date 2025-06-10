package com.happypaws.backend.petmanager.application.getOwnerPets;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.petmanager.application.PetMapper;
import com.happypaws.backend.petmanager.application.create.PetResponse;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetOwnerPetsQueryHandler {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public List<PetResponse> handle(GetOwnerPetsQuery request) {
        final var owner = userRepository.findById(request.ownerId());
        if (owner.isEmpty()) {
            throw new RuntimeException("Owner not found");
        }
        final var pets = petRepository.findAllByOwner(owner.get());

        return pets.stream().map(PetMapper::fromEntity).collect(Collectors.toList());
    }
}
