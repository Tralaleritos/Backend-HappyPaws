package com.happypaws.backend.offersmanager.application.offers.accept;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.authentication.presentation.dtos.UserResponse;
import com.happypaws.backend.offersmanager.infrastructure.repositories.OfferRepository;
import com.happypaws.backend.offersmanager.infrastructure.socketcontroller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AcceptOfferCommandHandler {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final NotificationController notificationController;

    public void handle(AcceptOfferCommand command) {
        final var offer = offerRepository.findById(command.offerId())
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        final var caregiver = userRepository.findById(command.caregiverId())
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        try {
            offer.accept(caregiver);
            offerRepository.save(offer);

            notificationController.notifyOfferAccepted(
                    offer.getOwner().getId(),
                    new OfferAcceptedResponse(
                            new UserResponse(
                                    caregiver.getId(),
                                    caregiver.getUsername(),
                                    caregiver.getImgUrl()),
                            offer.getId()
                    ));
        } catch (OptimisticLockingFailureException e) {
            notificationController.notifyOfferUnavailable(
                    caregiver.getId(),
                    offer.getId(),
                    "La oferta ya fue aceptada por otro cuidador."
            );
            throw new RuntimeException("Offer was accepted by another caregiver");
        }
    }
}
