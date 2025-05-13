package com.happypaws.backend.offersmanager.infrastructure.repositories;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.offersmanager.domain.caregivers.CaregiverAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CaregiverAvailabilityRepository extends JpaRepository<CaregiverAvailability, Long> {
    Optional<CaregiverAvailability> findByCaregiver(User user);

    @Query(value = """
        SELECT *
        FROM caregiver_availability ca
        WHERE (
            6371000 * acos(
                cos(radians(:latitude)) * 
                cos(radians(ca.latitude)) * 
                cos(radians(ca.longitude) - radians(:longitude)) + 
                sin(radians(:latitude)) * 
                sin(radians(ca.latitude))
            )
        ) <= 5000
        AND ca.available = true
    """, nativeQuery = true)
    List<CaregiverAvailability> findNearbyAvailableCaregivers(@Param("latitude") Double latitude,
                                                              @Param("longitude") Double longitude);

}
