package com.happypaws.backend.offersmanager.infrastructure.repositories;

import com.happypaws.backend.offersmanager.domain.offers.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o " +
            "WHERE o.status = 'ACCEPTED' " +
            "AND (o.owner.id = :userId OR o.caregiver.id = :userId) " +
            "ORDER BY o.createdDate DESC")
    List<Offer> findAcceptedOffersByUserId(@Param("userId") long userId);
}
