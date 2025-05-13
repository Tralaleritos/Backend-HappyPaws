package com.happypaws.backend.offersmanager.presentation.caregivers;

import com.happypaws.backend.offersmanager.application.caregivers.availability.SetCaregiverAvailabilityCommand;
import com.happypaws.backend.offersmanager.application.caregivers.availability.SetCaregiverAvailabilityCommandHandler;
import com.happypaws.backend.offersmanager.application.caregivers.create.CreateCaregiverAvailabilityCommand;
import com.happypaws.backend.offersmanager.application.caregivers.create.CreateCaregiverAvailabilityCommandHandler;
import com.happypaws.backend.offersmanager.application.caregivers.unavailable.SetCaregiverUnavailabilityCommand;
import com.happypaws.backend.offersmanager.application.caregivers.unavailable.SetCaregiverUnavailabilityCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caregivers-availability")
@RequiredArgsConstructor
public class CaregiversController {
    private final CreateCaregiverAvailabilityCommandHandler createCaregiverAvailabilityCommandHandler;
    private final SetCaregiverAvailabilityCommandHandler setCaregiverAvailabilityCommandHandler;
    private final SetCaregiverUnavailabilityCommandHandler setCaregiverUnavailabilityCommandHandler;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCaregiverAvailabilityRequest request) {
        try {
            final var command = new CreateCaregiverAvailabilityCommand(
                    request.caregiverId(),
                    request.locationName(),
                    request.locationLatitude(),
                    request.locationLongitude()
            );
            final var result = createCaregiverAvailabilityCommandHandler.handle(command);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("caregiver/{id}/available")
    public ResponseEntity<?> available(@PathVariable("id") long id, @RequestBody SetCaregiverAvailabilityRequest request) {
        try {
            final var command = new SetCaregiverAvailabilityCommand(
                    id,
                    request.locationName(),
                    request.locationLatitude(),
                    request.locationLongitude()
            );
            setCaregiverAvailabilityCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("caregiver/{id}/unavailable")
    public ResponseEntity<?> unavailable(@PathVariable("id") long id) {
        try {
            final var command = new SetCaregiverUnavailabilityCommand(id);
            setCaregiverUnavailabilityCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
