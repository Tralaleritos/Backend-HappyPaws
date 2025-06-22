package com.happypaws.backend.authentication.application.users.update;

import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateUserCommandHandler {
    private final UserRepository userRepository;

    public void handle(final UpdateUserCommand command) {
        final var user = userRepository.findById(command.userId());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        user.get()
                .update(
                        command.username(),
                        command.email(),
                        command.phoneNumber(),
                        command.imgUrl()
                );

        userRepository.save(user.get());
    }
}
