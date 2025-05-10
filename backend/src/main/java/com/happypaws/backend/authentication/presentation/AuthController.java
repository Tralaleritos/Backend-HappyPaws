package com.happypaws.backend.authentication.presentation;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.authentication.infrastructure.services.AuthenticationService;
import com.happypaws.backend.authentication.infrastructure.services.JwtService;
import com.happypaws.backend.authentication.presentation.dtos.LoginResponse;
import com.happypaws.backend.authentication.presentation.dtos.LoginUserDto;
import com.happypaws.backend.authentication.presentation.dtos.RegisterUserDto;
import com.happypaws.backend.authentication.presentation.dtos.VerifyUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterUserDto registerUserDto) {
        User registerUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticationUser = authenticationService.authenticate(loginUserDto);
        String token = jwtService.generateToken(authenticationUser);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code resend successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
