package com.happypaws.backend.offersmanager.domain.offers;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.petmanager.domain.Pet;
import com.happypaws.backend.shared.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "offers")
public class Offer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column=@Column(name = "location_name")),
            @AttributeOverride(name = "latitude", column = @Column(name = "location_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "location_longitude"))
    })
    private Location location;

    private String description;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Embedded
    private DateRange range;

    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "offers_services",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private User caregiver;

    @ManyToMany
    @JoinTable(
            name = "offers_pets",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    @Version
    private Long version;

    public static Offer Create(Location location,
                               String description,
                               DateRange range,
                               User owner,
                               List<Pet> pets,
                               BigDecimal price,
                               List<Service> services
    ) {
        return Offer.builder()
                .location(location)
                .description(description)
                .range(range)
                .owner(owner)
                .pets(pets)
                .status(OfferStatus.PENDING)
                .price(price)
                .services(services)
                .build();
    }

    public void cancel() {
        if (status == OfferStatus.PENDING) {
            status = OfferStatus.CANCELED;
            return;
        }
        throw new RuntimeException("Invalid offer cancelled");
    }

    public void accept(User caregiver) {
        if (status == OfferStatus.PENDING) {
            status = OfferStatus.ACCEPTED;
            this.caregiver = caregiver;
            return;
        }
        throw new RuntimeException("Invalid offer accepted");
    }

    public void complete() {
        if (status == OfferStatus.ACCEPTED) {
            status = OfferStatus.COMPLETED;
            return;
        }
        throw new RuntimeException("Invalid offer completed");
    }
}
