package com.happypaws.backend.offersmanager.presentation.offers;

import com.happypaws.backend.offersmanager.application.offers.accept.AcceptOfferCommand;
import com.happypaws.backend.offersmanager.application.offers.accept.AcceptOfferCommandHandler;
import com.happypaws.backend.offersmanager.application.offers.complete.CompleteOfferCommand;
import com.happypaws.backend.offersmanager.application.offers.complete.CompleteOfferCommandHandler;
import com.happypaws.backend.offersmanager.application.offers.create.CreateOfferCommand;
import com.happypaws.backend.offersmanager.application.offers.create.CreateOfferCommandHandler;
import com.happypaws.backend.offersmanager.application.offers.directOffer.DirectOfferCommand;
import com.happypaws.backend.offersmanager.application.offers.directOffer.DirectOfferCommandHandler;
import com.happypaws.backend.offersmanager.application.offers.getById.GetOfferByIdQuery;
import com.happypaws.backend.offersmanager.application.offers.getById.GetOfferByIdQueryHandler;
import com.happypaws.backend.offersmanager.domain.offers.DateRange;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {
    private final CreateOfferCommandHandler createOfferCommandHandler;
    private final GetOfferByIdQueryHandler getOfferByIdQueryHandler;
    private final AcceptOfferCommandHandler acceptOfferCommandHandler;
    private final CompleteOfferCommandHandler completeOfferCommandHandler;
    private final DirectOfferCommandHandler directOfferCommandHandler;

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody @Valid CreateOfferRequest request) {
        try {
            final var command = new CreateOfferCommand(
                    request.ownerId(),
                    new Location(request.locationName(), request.locationLatitude(), request.locationLongitude()),
                    request.description(),
                    new DateRange(request.date(), request.startTime(), request.endTime()),
                    request.pets(),
                    request.price(),
                    request.services()
            );
            final var response = createOfferCommandHandler.handle(command);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOfferById(@PathVariable("id") long id) {
        try {
            final var query = new GetOfferByIdQuery(id);
            final var response = getOfferByIdQueryHandler.handle(query);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('CAREGIVER')")
    @PostMapping("/accept")
    public ResponseEntity<?> acceptOffer(@RequestBody AcceptOfferRequest request) {
        try {
            final var command = new AcceptOfferCommand(
                    request.offerId(),
                    request.caregiverId()
            );
            acceptOfferCommandHandler.handle(command);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('CAREGIVER')")
    @PostMapping("{id}/complete")
    public ResponseEntity<?> completeOffer(@PathVariable long id) {
        try {
            final var command = new CompleteOfferCommand(
                    id
            );
            completeOfferCommandHandler.handle(command);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/direct-offer")
    public ResponseEntity<?> directOfferToCaregiver(final DirectOfferRequest request) {
        try {
            final var command = new DirectOfferCommand(
                    request.ownerId(),
                    request.caregiverId(),
                    new Location(request.locationName(), request.locationLatitude(), request.locationLongitude()),
                    request.description(),
                    new DateRange(request.date(), request.startTime(), request.endTime()),
                    request.pets(),
                    request.price(),
                    request.services()
            );
            final var response = directOfferCommandHandler.handle(command);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
