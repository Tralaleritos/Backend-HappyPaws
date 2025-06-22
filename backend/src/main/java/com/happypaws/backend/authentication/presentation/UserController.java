package com.happypaws.backend.authentication.presentation;

import com.happypaws.backend.authentication.application.users.update.UpdateUserCommand;
import com.happypaws.backend.authentication.application.users.update.UpdateUserCommandHandler;
import com.happypaws.backend.authentication.infrastructure.services.AuthenticationService;
import com.happypaws.backend.authentication.presentation.dtos.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final UpdateUserCommandHandler updateUserCommandHandler;

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
}
