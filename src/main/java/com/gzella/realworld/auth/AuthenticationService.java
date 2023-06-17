package com.gzella.realworld.auth;

import com.gzella.realworld.config.JwtService;
import com.gzella.realworld.persistence.entity.user.Role;
import com.gzella.realworld.persistence.entity.user.User;
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

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                 .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        // todo specify exception
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincialName = authentication.getName();
        var user = repository.findByEmail(currentPrincialName).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .email(user.getEmail())
                .token(jwtToken)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}