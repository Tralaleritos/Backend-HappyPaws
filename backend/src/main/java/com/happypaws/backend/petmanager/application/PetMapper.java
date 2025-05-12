package com.happypaws.backend.petmanager.application;

import com.happypaws.backend.petmanager.application.create.PetResponse;
import com.happypaws.backend.petmanager.domain.Pet;

public class PetMapper {

    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getDescription(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getAge(),
                pet.getImgUrl()
        );
    }
}
