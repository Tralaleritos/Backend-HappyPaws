package com.happypaws.backend.offersmanager.application.services.create;

import com.happypaws.backend.offersmanager.infrastructure.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.happypaws.backend.offersmanager.domain.offers.Service.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateServiceCommandHandler {
    private final ServiceRepository serviceRepository;

    public void handle(final CreateServiceCommand command) {
        final var service = Create(
                command.name(),
                command.description()
        );
        serviceRepository.save(service);
    }
}
