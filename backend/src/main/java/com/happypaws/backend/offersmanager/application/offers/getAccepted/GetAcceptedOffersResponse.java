package com.happypaws.backend.offersmanager.application.offers.getAccepted;
import com.happypaws.backend.authentication.presentation.dtos.UserResponse;

import com.happypaws.backend.offersmanager.application.services.create.ServiceResponse;
import com.happypaws.backend.offersmanager.domain.offers.DateRange;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import com.happypaws.backend.petmanager.application.create.PetResponse;


import java.math.BigDecimal;
import java.util.List;

public record GetAcceptedOffersResponse(
        long id,
        BigDecimal price,
        String description,
        Location location,
        DateRange range,
        List<PetResponse> pets,
        UserResponse owner,
        UserResponse caregiver,
        List<ServiceResponse> services
) {
}
