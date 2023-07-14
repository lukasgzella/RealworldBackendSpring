package com.gzella.realworld.business.service;

import com.gzella.realworld._config.JwtService;
import com.gzella.realworld.business.dto.requests.LoginRequest;
import com.gzella.realworld.business.dto.requests.RegistrationRequest;
import com.gzella.realworld.business.dto.requests.UpdateRequest;
import com.gzella.realworld.business.dto.responses.LoginResponse;
import com.gzella.realworld.persistence.entity.Role;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // ACCORDING TO SPEC:

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .email(user.getEmail())
                .token(jwtToken)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    public LoginResponse registerUser(RegistrationRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .email(user.getEmail())
                .token(jwtToken)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    public LoginResponse getCurrentUser() {
        var user = retrieveCurrentUserFromDb();
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .email(user.getEmail())
                .token(jwtToken)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    public LoginResponse updateUser(UpdateRequest request) {
        var user = retrieveCurrentUserFromDb();

        if (isEmailNotUnique(user.getEmail(),request.getEmail())) {
            throw new IllegalArgumentException("Provided email already exists");
        }
        if (isUsernameNotUnique(user.getUsername(),request.getUsername())) {
            throw new IllegalArgumentException("Provided username already exists");
        }

        User updatedUser = repository.save(updateRequestedFields(user, request));

        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .email(updatedUser.getEmail())
                .token(jwtToken)
                .username(updatedUser.getUsername())
                .bio(updatedUser.getBio())
                .image(updatedUser.getImage())
                .build();
    }

    private User updateRequestedFields(User user, UpdateRequest request) {
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getImage() != null) {
            user.setImage(request.getImage());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        return user;
    }

    private boolean isEmailNotUnique(String email, String newEmail) {
        return repository.existsByEmail(newEmail) && !email.equals(newEmail);
    }

    private boolean isUsernameNotUnique(String username, String newUsername) {
        return repository.existsByUsername(newUsername) && !username.equals(newUsername);
    }

    private User retrieveCurrentUserFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincialName = authentication.getName();
        return repository.findByEmail(currentPrincialName).orElseThrow();
    }

}