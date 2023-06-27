package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    //    auth optional
    @GetMapping("/api/articles")
    public ResponseEntity<LoginResponse> currentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }

}
