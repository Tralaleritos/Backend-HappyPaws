package com.happypaws.backend.offersmanager.infrastructure.repositories;

import com.happypaws.backend.offersmanager.domain.offers.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
