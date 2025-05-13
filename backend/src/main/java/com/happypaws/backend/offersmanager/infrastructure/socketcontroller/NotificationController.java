package com.happypaws.backend.offersmanager.infrastructure.socketcontroller;

import com.happypaws.backend.offersmanager.application.offers.create.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(long caregiverId, OfferResponse offerResponse) {
        messagingTemplate.convertAndSend("/topic/offers/" + caregiverId, offerResponse);
    }
}
