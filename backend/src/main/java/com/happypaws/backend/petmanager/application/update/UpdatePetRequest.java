package com.happypaws.backend.petmanager.application.update;

import com.happypaws.backend.petmanager.domain.Species;

public record UpdatePetRequest(
        long id,
        String name,
        String description,
        Species species,
        String breed,
        int age,
        String imgUrl
) {
}
