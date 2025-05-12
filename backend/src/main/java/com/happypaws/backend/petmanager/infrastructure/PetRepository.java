package com.happypaws.backend.petmanager.infrastructure;

import com.happypaws.backend.petmanager.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
