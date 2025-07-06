package com.happypaws.backend.offersmanager.presentation.offers;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DirectOfferRequest(
        long ownerId,
        long caregiverId,
        String locationName,
        Double locationLatitude,
        Double locationLongitude,
        String description,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        List<Long> pets,
        @Min(value = 1)
        BigDecimal price,
        List<Long> services
) {
}
