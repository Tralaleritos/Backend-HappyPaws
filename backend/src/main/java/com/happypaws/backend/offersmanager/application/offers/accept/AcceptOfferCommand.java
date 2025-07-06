package com.happypaws.backend.offersmanager.application.offers.accept;

public record AcceptOfferCommand(long offerId, long caregiverId) {
}
