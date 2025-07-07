package com.happypaws.backend.authentication.application.users.getAllOwners;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.petmanager.application.create.PetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllOwnersQueryHandler {
    private final UserRepository userRepository;

    public List<OwnerDetailResponse> handle(GetAllOwnersQuery query) {
        final var owners = userRepository.findUsersByRole("OWNER");

        return owners.stream()
                .map(owner -> new OwnerDetailResponse(
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
                ))
                .toList();
    }
}
