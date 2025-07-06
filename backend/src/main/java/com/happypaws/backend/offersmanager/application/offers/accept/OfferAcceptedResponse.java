package com.happypaws.backend.offersmanager.application.offers.accept;

import com.happypaws.backend.authentication.presentation.dtos.UserResponse;

public record OfferAcceptedResponse(
        UserResponse caregiver,
        long offerId
) {
}
