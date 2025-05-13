package com.happypaws.backend.offersmanager.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OfferWebSocketReceiver {

    @MessageMapping("/accept-offer")
    public void acceptOffer(@Payload long offerId) {
        System.out.println("Accepted offer Id: " + offerId);
    }
}
