package com.happypaws.backend.offersmanager.domain.offers;

import jakarta.persistence.Embeddable;

@Embeddable
public record Location(
        String name,
        Double latitude,
        Double longitude
) {
}
