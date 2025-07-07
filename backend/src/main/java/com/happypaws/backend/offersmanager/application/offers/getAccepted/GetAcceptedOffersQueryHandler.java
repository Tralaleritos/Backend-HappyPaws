package com.happypaws.backend.offersmanager.application.offers.getAccepted;


import com.happypaws.backend.authentication.presentation.dtos.UserResponse;
import com.happypaws.backend.offersmanager.application.services.ServiceMapper;
import com.happypaws.backend.offersmanager.domain.offers.OfferStatus;
import com.happypaws.backend.offersmanager.infrastructure.repositories.OfferRepository;
import com.happypaws.backend.petmanager.application.PetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAcceptedOffersQueryHandler {
    private final OfferRepository offerRepository;

    public List<GetAcceptedOffersResponse> handle(GetAcceptedOffersQuery query) {
        final var acceptedOffers = offerRepository.findAcceptedOffersByUserId(query.userId());

        return acceptedOffers.stream()
                .map(offer -> new GetAcceptedOffersResponse(
                        offer.getId(),
                        offer.getPrice(),
                        offer.getDescription(),
                        offer.getLocation(),
                        offer.getRange(),
                        offer.getPets().stream().map(PetMapper::fromEntity).toList(),
                        new UserResponse(
                                offer.getOwner().getId(),
                                offer.getOwner().getUsername(),
                                offer.getOwner().getImgUrl()
                        ),
                        new UserResponse(
                                offer.getCaregiver().getId(),
                                offer.getCaregiver().getUsername(),
                                offer.getCaregiver().getImgUrl()
                        ),
                        offer.getServices().stream().map(ServiceMapper::fromEntity).toList()
                ))
                .toList();
    }
}