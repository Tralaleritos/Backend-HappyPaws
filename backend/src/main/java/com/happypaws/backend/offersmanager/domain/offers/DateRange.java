package com.happypaws.backend.offersmanager.domain.offers;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
public record DateRange(
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
}
