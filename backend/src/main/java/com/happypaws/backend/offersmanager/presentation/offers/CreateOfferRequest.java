package com.happypaws.backend.offersmanager.presentation.offers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CreateOfferRequest(
        long ownerId,
        String locationName,
        Double locationLatitude,
        Double locationLongitude,
        String description,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        List<Long> pets
) {
}
