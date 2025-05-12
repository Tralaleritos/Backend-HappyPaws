package com.happypaws.backend.offersmanager.application.create;

import com.happypaws.backend.authentication.presentation.dtos.UserResponse;
import com.happypaws.backend.offersmanager.domain.DateRange;
import com.happypaws.backend.offersmanager.domain.Location;
import com.happypaws.backend.petmanager.application.create.PetResponse;

import java.util.List;

public record OfferResponse(
        long id,
        String description,
        Location location,
        DateRange range,
        List<PetResponse> pets,
        UserResponse owner
) {
}
