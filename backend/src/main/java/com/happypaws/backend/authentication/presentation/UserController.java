package com.happypaws.backend.authentication.presentation;

import com.happypaws.backend.authentication.application.users.getAllOwners.GetAllOwnersQuery;
import com.happypaws.backend.authentication.application.users.getAllOwners.GetAllOwnersQueryHandler;
import com.happypaws.backend.authentication.application.users.location.UpdateUserLocationCommand;
import com.happypaws.backend.authentication.application.users.location.UpdateUserLocationCommandHandler;
import com.happypaws.backend.authentication.application.users.update.UpdateUserCommand;
import com.happypaws.backend.authentication.application.users.update.UpdateUserCommandHandler;
import com.happypaws.backend.authentication.infrastructure.services.AuthenticationService;
import com.happypaws.backend.authentication.presentation.dtos.UpdateUserLocationRequest;
import com.happypaws.backend.authentication.presentation.dtos.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final UpdateUserCommandHandler updateUserCommandHandler;
    private final UpdateUserLocationCommandHandler updateUserLocationCommandHandler;
    private final GetAllOwnersQueryHandler getAllOwnersQueryHandler;

    @GetMapping("/me")
    public ResponseEntity<?> geUserFromToken(
            @RequestHeader("Authorization") String token
    ) {
        try {
            final var user = authenticationService.me(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('CAREGIVER')")
    @GetMapping("/owners/details")
    public ResponseEntity<?> getAllOwnersDetails() {
        try {
            final var query = new GetAllOwnersQuery();
            final var response = getAllOwnersQueryHandler.handle(query);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") long id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        try {
            final var command = new UpdateUserCommand(
                    id,
                    updateUserRequest.username(),
                    updateUserRequest.email(),
                    updateUserRequest.phoneNumber(),
                    updateUserRequest.imgUrl());
            updateUserCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping("{id}/location")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") long id,
            @RequestBody UpdateUserLocationRequest updateUserLocationRequest
    ) {
        try {
            final var command = new UpdateUserLocationCommand(
                    id,
                    updateUserLocationRequest.latitude(),
                    updateUserLocationRequest.longitude());
            updateUserLocationCommandHandler.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}