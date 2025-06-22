package com.happypaws.backend.offersmanager.application.offers;

import com.happypaws.backend.offersmanager.application.offers.create.ServiceResponse;
import com.happypaws.backend.offersmanager.domain.offers.Service;

public class ServiceMapper {
    public static ServiceResponse fromEntity(Service service) {
        return new ServiceResponse(service.getId(), service.getName(), service.getDescription());
    }
}
