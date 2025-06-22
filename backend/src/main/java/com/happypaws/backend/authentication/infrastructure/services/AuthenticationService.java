package com.happypaws.backend.authentication.infrastructure.services;

import com.happypaws.backend.authentication.domain.User;
import com.happypaws.backend.authentication.infrastructure.repositories.RoleRepository;
import com.happypaws.backend.authentication.infrastructure.repositories.UserRepository;
import com.happypaws.backend.authentication.presentation.dtos.AuthUserResponse;
import com.happypaws.backend.authentication.presentation.dtos.LoginUserDto;
import com.happypaws.backend.authentication.presentation.dtos.RegisterUserDto;
import com.happypaws.backend.authentication.presentation.dtos.VerifyUserDto;
import com.happypaws.backend.shared.infrastructure.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public User signUp(RegisterUserDto registerUserDto) {
        var role = roleRepository.findByName(registerUserDto.getRole());

        if (role.isEmpty()) {
            throw new UsernameNotFoundException(registerUserDto.getUsername());
        }

        User user = User.builder()
                .username(registerUserDto.getUsername())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .email(registerUserDto.getEmail())
                .phoneNumber(registerUserDto.getPhoneNumber())
                .roles(List.of(role.get()))
                .imgUrl(registerUserDto.getImgUrl())
                .build();
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(loginUserDto.getEmail()));

        if (!user.isEnabled()){
            throw new RuntimeException("User is not verify");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return user;
    }

    public AuthUserResponse me(String token) {
        final String jwtToken = token.substring(7);
        final var userEmail = jwtService.getUserEmailFromToken(jwtToken);
        if (userEmail.isEmpty()) {
            throw new RuntimeException("Use not found");
        }

        final var user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new RuntimeException("Use not found");
        }

        return new AuthUserResponse(
                user.get().getId(),
                user.get().getUserName(),
                user.get().getEmail(),
                user.get().getPhoneNumber(),
                user.get().getImgUrl()
        );
    }


    public void verifyUser(VerifyUserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(userDto.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("User is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();

        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
