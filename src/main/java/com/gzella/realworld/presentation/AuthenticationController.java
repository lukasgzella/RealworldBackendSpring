package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.requests.LoginRequest;
import com.gzella.realworld.business.dto.requests.RegistrationRequest;
import com.gzella.realworld.business.dto.requests.UpdateRequest;
import com.gzella.realworld.business.dto.responses.LoginResponse;
import com.gzella.realworld.business.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

//    spec endpoints - authentication, registration

    //    auth permitAll
    @PostMapping("/api/users/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    //    registration permitAll
    @PostMapping("/api/users")
    public ResponseEntity<LoginResponse> registerUser(
            @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    //    auth required
    @GetMapping("/api/user")
    public ResponseEntity<LoginResponse> currentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }

    //    auth required
    @PutMapping("/api/user")
    public ResponseEntity<LoginResponse> updateUser(
            @RequestBody UpdateRequest request
    ) {
        return ResponseEntity.ok(authenticationService.updateUser(request));
    }
}
