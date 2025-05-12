package com.happypaws.backend.offersmanager.application.create;

import com.happypaws.backend.offersmanager.domain.DateRange;
import com.happypaws.backend.offersmanager.domain.Location;

import java.util.List;

public record CreateOfferCommand(
        long ownerId,
        Location location,
        String description,
        DateRange range,
        List<Long> pets
) {
}
