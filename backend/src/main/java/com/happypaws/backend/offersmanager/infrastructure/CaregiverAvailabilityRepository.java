package com.happypaws.backend.offersmanager.infrastructure;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.offersmanager.domain.caregivers.CaregiverAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaregiverAvailabilityRepository extends JpaRepository<CaregiverAvailability, Long> {
    Optional<CaregiverAvailability> findByCaregiver(User user);
}
