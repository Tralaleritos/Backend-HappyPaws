package com.happypaws.backend.offersmanager.application.offers;

import com.happypaws.backend.authentication.presentation.dtos.UserResponse;
import com.happypaws.backend.offersmanager.application.offers.create.OfferResponse;
import com.happypaws.backend.offersmanager.domain.offers.Offer;
import com.happypaws.backend.petmanager.application.PetMapper;

public class OfferMapper {
    public static OfferResponse fromEntity(final Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getDescription(),
                offer.getLocation(),
                offer.getRange(),
                offer.getPets().stream().map(PetMapper::fromEntity).toList(),
                new UserResponse(
                        offer.getOwner().getId(),
                        offer.getOwner().getUsername(),
                        offer.getOwner().getImgUrl()
                )
        );
    }
}
