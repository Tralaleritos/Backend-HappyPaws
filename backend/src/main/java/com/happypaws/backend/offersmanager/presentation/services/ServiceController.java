package com.happypaws.backend.offersmanager.presentation.services;

import com.happypaws.backend.offersmanager.application.services.create.CreateServiceCommand;
import com.happypaws.backend.offersmanager.application.services.create.CreateServiceCommandHandler;
import com.happypaws.backend.offersmanager.application.services.getAll.GetAllServicesQuery;
import com.happypaws.backend.offersmanager.application.services.getAll.GetAllServicesQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final CreateServiceCommandHandler createServiceCommandHandler;
    private final GetAllServicesQueryHandler getAllServicesQueryHandler;

    @GetMapping
    public ResponseEntity<?> getAllServices(){
        final var query = new GetAllServicesQuery();
        final var response = getAllServicesQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createService(@RequestBody final CreateServiceRequest createServiceRequest){
        try {
            final var command = new CreateServiceCommand(createServiceRequest.name(), createServiceRequest.description());
            createServiceCommandHandler.handle(command);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
