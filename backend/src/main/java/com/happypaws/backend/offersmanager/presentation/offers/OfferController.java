package com.happypaws.backend.offersmanager.presentation.offers;

import com.happypaws.backend.offersmanager.application.offers.create.CreateOfferCommand;
import com.happypaws.backend.offersmanager.application.offers.create.CreateOfferCommandHandler;
import com.happypaws.backend.offersmanager.application.offers.getById.GetOfferByIdQuery;
import com.happypaws.backend.offersmanager.application.offers.getById.GetOfferByIdQueryHandler;
import com.happypaws.backend.offersmanager.domain.offers.DateRange;
import com.happypaws.backend.offersmanager.domain.offers.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {
    private final CreateOfferCommandHandler createOfferCommandHandler;
    private final GetOfferByIdQueryHandler getOfferByIdQueryHandler;

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody CreateOfferRequest request) {
        try {
            final var command = new CreateOfferCommand(
                    request.ownerId(),
                    new Location(request.locationName(), request.locationLatitude(), request.locationLongitude()),
                    request.description(),
                    new DateRange(request.date(), request.startTime(), request.endTime()),
                    request.pets()
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
}
