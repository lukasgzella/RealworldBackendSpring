package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.LoginResponse;
import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.persistence.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    //    Authentication optional, will return multiple articles, ordered by most recent first
    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> getArticles(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //   permitAll, will return single article
    @GetMapping("/api/articles/:slug")
    public ResponseEntity<List<Article>> getArticles(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }
}
