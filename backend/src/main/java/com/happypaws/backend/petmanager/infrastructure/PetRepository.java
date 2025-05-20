package com.happypaws.backend.petmanager.infrastructure;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.petmanager.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwner(User owner);
}
