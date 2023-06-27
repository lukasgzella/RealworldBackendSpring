package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.LoginResponse;
import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.business.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //    auth optional, return Profile
    @GetMapping("/api/profiles/{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //    auth required, return Profile
    @PostMapping("/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.followUser(username));
    }

    //    auth required, return Profile
    @DeleteMapping("/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> unfollow(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.unfollowUser(username));
    }
}
