package com.happypaws.backend.offersmanager.application.offers.complete;

import com.happypaws.backend.offersmanager.infrastructure.repositories.OfferRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompleteOfferCommandHandler {
    private final OfferRepository offerRepository;
    private final NotificationController notificationController;

    public void handle(final CompleteOfferCommand command) {
        final var offer = offerRepository.findById(command.offerId())
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));
        offer.complete();
        offerRepository.save(offer);

        notificationController.notifyOfferCompleted(
                offer.getOwner().getId(),
                offer.getId(),
                "Offer completed successfully"
        );
    }
}
