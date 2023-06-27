package com.gzella.realworld.presentation;

import com.gzella.realworld.business.service.UserService;
import com.gzella.realworld.business.dto.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //    auth optional, return Profile
    @GetMapping("/api/profiles/{username}")
    public ResponseEntity<LoginResponse> currentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }

    //    auth required, return Profile
    @PostMapping("/api/profiles/{username}/follow")
    public ResponseEntity<LoginResponse> currentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }

    //    auth required, return Profile
    @DeleteMapping("/api/profiles/{username}/follow")
    public ResponseEntity<LoginResponse> currentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }

}
