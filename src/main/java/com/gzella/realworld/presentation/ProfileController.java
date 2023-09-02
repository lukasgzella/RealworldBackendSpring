package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.business.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // Get Profile
    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    // Follow User
    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> followUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.followUser(username));
    }

    // Unfollow User
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> unfollowUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.unfollowUser(username));
    }
}