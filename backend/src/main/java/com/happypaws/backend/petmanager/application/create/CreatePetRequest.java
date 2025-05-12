package com.happypaws.backend.petmanager.application.create;

import com.happypaws.backend.petmanager.domain.Species;

public record CreatePetRequest(
        String name,
        String description,
        Species species,
        String breed,
        int age,
        long ownerId,
        String imgUrl
) {}
