package com.happypaws.backend.authentication.application.users.getOwnerDetail;

import com.happypaws.backend.petmanager.application.create.PetResponse;

import java.util.List;

public record OwnerDetailResponse(
        long id,
        String username,
        String imgUrl,
        List<PetResponse> pets
) {}
