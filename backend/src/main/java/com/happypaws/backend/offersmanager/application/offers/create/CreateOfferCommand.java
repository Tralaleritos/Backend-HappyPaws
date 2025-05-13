package com.happypaws.backend.offersmanager.application.offers.create;

import com.happypaws.backend.offersmanager.domain.offers.DateRange;
import com.happypaws.backend.offersmanager.domain.offers.Location;

import java.util.List;

public record CreateOfferCommand(
        long ownerId,
        Location location,
        String description,
        DateRange range,
        List<Long> pets
) {
}
