package com.happypaws.backend.petmanager.application.create;

import com.happypaws.backend.petmanager.domain.Species;

public record PetResponse(
        long id,
        String name,
        String description,
        Species species,
        String breed,
        int age,
        String imgUrl
) {
}
