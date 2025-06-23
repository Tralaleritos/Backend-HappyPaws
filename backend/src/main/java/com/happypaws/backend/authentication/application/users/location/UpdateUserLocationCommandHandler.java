package com.happypaws.backend.authentication.application.users.location;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserLocationCommandHandler {
    private final UserRepository userRepository;

    public void handle(final UpdateUserLocationCommand command) {
        final var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateLocation(command.latitude(), command.longitude());

        userRepository.save(user);
    }
}
