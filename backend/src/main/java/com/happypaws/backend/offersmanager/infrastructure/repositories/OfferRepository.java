package com.happypaws.backend.offersmanager.infrastructure.repositories;

import com.happypaws.backend.offersmanager.domain.offers.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
