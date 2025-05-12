package com.happypaws.backend.offersmanager.application.getById;

import com.happypaws.backend.offersmanager.application.OfferMapper;
import com.happypaws.backend.offersmanager.application.create.OfferResponse;
import com.happypaws.backend.offersmanager.infrastructure.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetOfferByIdQueryHandler {
    private final OfferRepository offerRepository;

    public OfferResponse handle(GetOfferByIdQuery query) {
        final var offer = offerRepository.findById(query.id());

        if (offer.isEmpty()) {
            throw new RuntimeException("Offer not found");
        }

        return OfferMapper.fromEntity(offer.get());
    }
}
