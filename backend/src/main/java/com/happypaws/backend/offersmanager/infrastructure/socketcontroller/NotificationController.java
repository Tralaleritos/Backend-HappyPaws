package com.happypaws.backend.offersmanager.infrastructure.socketcontroller;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.authentication.presentation.dtos.CaregiverUnavailableResponse;
import com.happypaws.backend.authentication.presentation.dtos.CaregiversNearbyResponse;
import com.happypaws.backend.offersmanager.application.offers.create.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public void sendNotification(long caregiverId, OfferResponse offerResponse) {
        messagingTemplate.convertAndSend("/topic/offers/" + caregiverId, offerResponse);
    }

    public void notifyNearbyPetOwners(long caregiverId, double caregiverLat, double caregiverLon) {
        List<User> nearbyOwners = userRepository.findNearbyPetOwners(caregiverLat, caregiverLon);
        var caregiver = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("caregiver not found"));

        for (User owner : nearbyOwners) {
            messagingTemplate.convertAndSend("/topic/notifications/" + owner.getId(),
                    new CaregiversNearbyResponse(caregiver.getId(), caregiver.getUserName(),
                            caregiver.getImgUrl(), caregiverLat, caregiverLon));
        }
    }

    // NUEVO: Notificar cuando un caregiver se vuelve no disponible
    public void notifyNearbyPetOwnersUnavailable(long caregiverId, double caregiverLat, double caregiverLon) {
        List<User> nearbyOwners = userRepository.findNearbyPetOwners(caregiverLat, caregiverLon);
        var caregiver = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("caregiver not found"));

        for (User owner : nearbyOwners) {
            messagingTemplate.convertAndSend("/topic/notifications/" + owner.getId(),
                    new CaregiverUnavailableResponse(caregiver.getId()));
        }
    }
}
