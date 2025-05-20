package com.happypaws.backend.petmanager.presentation;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.petmanager.application.create.CreatePetCommand;
import com.happypaws.backend.petmanager.application.create.CreatePetCommandHandler;
import com.happypaws.backend.petmanager.application.create.CreatePetRequest;
import com.happypaws.backend.petmanager.application.delete.DeletePetCommand;
import com.happypaws.backend.petmanager.application.delete.DeletePetCommandHandler;
import com.happypaws.backend.petmanager.application.getById.GetPetByIdQuery;
import com.happypaws.backend.petmanager.application.getById.GetPetByIdQueryHandler;
import com.happypaws.backend.petmanager.application.update.UpdatePetCommand;
import com.happypaws.backend.petmanager.application.update.UpdatePetCommandHandler;
import com.happypaws.backend.petmanager.application.update.UpdatePetRequest;
import com.happypaws.backend.petmanager.domain.Pet;
import com.happypaws.backend.petmanager.infrastructure.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {
    private final CreatePetCommandHandler createPetCommandHandler;
    private final UpdatePetCommandHandler updatePetCommandHandler;
    private final GetPetByIdQueryHandler getPetByIdQueryHandler;
    private final DeletePetCommandHandler deletePetCommandHandler;
    private final PetRepository petRepository;

    @GetMapping("/my")
    public ResponseEntity<?> getMyPets(@AuthenticationPrincipal User user) {
        try {
            List<Pet> pets = petRepository.findAllByOwner(user);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las mascotas: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody CreatePetRequest createPetRequest) {
        try {
            final var command = new CreatePetCommand(createPetRequest);
            final var response = createPetCommandHandler.handle(command);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPetById(@PathVariable("id") long id) {
        try {
            final var query = new GetPetByIdQuery(id);
            final var response = getPetByIdQueryHandler.handle(query);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePet(@PathVariable("id") long id, @RequestBody UpdatePetRequest updatePetRequest) {
        try {
            final var command = new UpdatePetCommand(updatePetRequest);
            updatePetCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePet(@PathVariable("id") long id) {
        try {
            final var command = new DeletePetCommand(id);
            deletePetCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
