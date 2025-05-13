package com.happypaws.backend.offersmanager.domain.caregivers;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import com.happypaws.backend.shared.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "caregiver_availability")
public class CaregiverAvailability extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "caregiver_id", nullable = false)
    private User caregiver;

    @Embedded
    private Location location;

    private boolean available;

    public static CaregiverAvailability Create(User caregiver, Location location, boolean available) {
        return CaregiverAvailability.builder()
                .caregiver(caregiver)
                .location(location)
                .available(available)
                .build();
    }

    public void setAvailable(Location location) {
        this.location = location;
        this.available = true;
    }

    public void unavailable() {
        this.available = false;
    }
}
