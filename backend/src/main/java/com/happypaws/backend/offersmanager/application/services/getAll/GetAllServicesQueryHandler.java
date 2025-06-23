package com.happypaws.backend.offersmanager.application.services.getAll;

import com.happypaws.backend.offersmanager.application.services.ServiceMapper;
import com.happypaws.backend.offersmanager.application.services.create.ServiceResponse;
import com.happypaws.backend.offersmanager.infrastructure.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAllServicesQueryHandler {
    private final ServiceRepository serviceRepository;

    public List<ServiceResponse> handle(GetAllServicesQuery query) {
        return serviceRepository.findAll()
                .stream().map(ServiceMapper::fromEntity)
                .toList();
    }
}
