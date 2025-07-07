package com.happypaws.backend.authentication.application.users.getAllOwners;

import com.happypaws.backend.petmanager.application.create.PetResponse;

import java.util.List;

public record OwnerDetailResponse(
        long id,
        String username,
        String imgUrl,
        List<PetResponse> pets
) {
}
