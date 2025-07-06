package com.happypaws.backend.authentication.application.users.getOwnerDetail;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.petmanager.application.create.PetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetOwnerDetailQueryHandler {
    private final UserRepository userRepository;

    public OwnerDetailResponse handle(GetOwnerDetailQuery query) {
        final var owner = userRepository.findById(query.ownerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new OwnerDetailResponse(
                owner.getId(),
                owner.getUserName(),
                owner.getImgUrl(),
                owner.getPets().stream().map(p -> new PetResponse(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getSpecies(),
                        p.getBreed(),
                        p.getAge(),
                        p.getImgUrl()
                )).toList()
        );
    }
}
