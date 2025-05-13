package com.happypaws.backend.offersmanager.infrastructure.services;

import com.happypaws.backend.offersmanager.application.offers.create.AvailableCaregivers;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import com.happypaws.backend.offersmanager.infrastructure.repositories.CaregiverAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AvailableCaregiversImpl implements AvailableCaregivers {
    private final CaregiverAvailabilityRepository caregiverAvailabilityRepository;

    @Override
    public List<Long> getAvailableCaregivers(Location location) {
        return caregiverAvailabilityRepository
                .findNearbyAvailableCaregivers(location.latitude(), location.longitude())
                .stream()
                .map(ca -> ca.getCaregiver().getId())
                .toList();
    }
}
