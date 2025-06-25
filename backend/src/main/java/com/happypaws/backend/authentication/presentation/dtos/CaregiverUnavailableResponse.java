package com.happypaws.backend.authentication.presentation.dtos;

public class CaregiverUnavailableResponse {
    private final Long caregiverId;
    private final String type = "CAREGIVER_UNAVAILABLE";

    public CaregiverUnavailableResponse(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

    public Long getCaregiverId() {
        return caregiverId;
    }

    public String getType() {
        return type;
    }
}

