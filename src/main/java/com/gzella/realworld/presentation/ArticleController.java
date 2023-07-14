package com.gzella.realworld.presentation;

import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.business.dto.responses.MultipleArticleResponse;
import com.gzella.realworld.business.service.ArticleService;
import com.gzella.realworld.persistence.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //    Authentication optional, will return multiple articles, ordered by most recent first
    @GetMapping("/api/articles")
    public ResponseEntity<MultipleArticleResponse> getArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favorited,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(articleService.getArticles(tag, author, favorited, limit, offset));
    }

    //   permitAll, will return single article
    @GetMapping("/api/articles/{slug}")
    public ResponseEntity<Map<String,ArticleResponse>> getArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(Map.of("article", articleService.getArticle(slug)));
    }





    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @GetMapping("/api/articles/feed")
    public ResponseEntity<MultipleArticleResponse> feedArticles(
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(articleService.getArticles(limit, offset));
    }






    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @PostMapping("/api/articles")
    public ResponseEntity<List<Article>> createArticle(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @PutMapping("/api/articles/:slug")
    public ResponseEntity<List<Article>> updateArticle(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //  Auth required
    @DeleteMapping("/api/articles/:slug")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }
}