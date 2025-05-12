package com.happypaws.backend.offersmanager.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Location(
        String name,
        Double latitude,
        Double longitude
) {
}
