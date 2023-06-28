package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.LoginResponse;
import com.gzella.realworld.business.dto.responses.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    //    auth optional, return Profile
    @GetMapping("/api/articles/{username}")
    public ResponseEntity<List<Article>> getProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

}
