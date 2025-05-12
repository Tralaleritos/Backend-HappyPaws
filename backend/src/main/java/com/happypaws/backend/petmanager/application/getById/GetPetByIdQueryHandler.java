package com.happypaws.backend.petmanager.application.getById;

import com.happypaws.backend.petmanager.application.PetMapper;
import com.happypaws.backend.petmanager.application.create.PetResponse;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPetByIdQueryHandler {
    private final PetRepository petRepository;

    public PetResponse handle(GetPetByIdQuery getPetByIdQuery) {
        final var pet = petRepository.findById(getPetByIdQuery.id());
        if (pet.isEmpty()){
            throw new RuntimeException("Pet not found");
        }
        return PetMapper.fromEntity(pet.get());
    }
}
