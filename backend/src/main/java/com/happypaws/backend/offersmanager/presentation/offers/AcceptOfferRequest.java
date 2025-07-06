package com.happypaws.backend.offersmanager.presentation.offers;

public record AcceptOfferRequest(
        long offerId,
        long caregiverId
) {
}
