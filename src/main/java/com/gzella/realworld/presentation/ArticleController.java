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

@RequestMapping("/api/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //    List Articles
    //    Authentication optional, will return multiple articles, ordered by most recent first
    @GetMapping
    public ResponseEntity<MultipleArticleResponse> listArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favorited,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(articleService.getArticles(tag, author, favorited, limit, offset));
    }

    //   Feed Articles
    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @GetMapping("/feed")
    public ResponseEntity<MultipleArticleResponse> feedArticles(
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(articleService.getArticlesFromFollowedUsers(limit, offset));
    }

    //      Get Article
    //   permitAll, will return single article
    @GetMapping("/{slug}")
    public ResponseEntity<Map<String, ArticleResponse>> getArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(Map.of("article", articleService.getArticle(slug)));
    }


    //      Create Article
    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @PostMapping
    public ResponseEntity<MultipleArticleResponse> createArticle(
            @PathVariable("username") String username,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(articleService.getArticlesFromFollowedUsers(limit, offset));
    }

    //      Update Article
    //   Authentication required, will return multiple articles created by followed users,
    //   ordered by most recent first.
    @PutMapping("/{slug}")
    public ResponseEntity<List<Article>> updateArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //      Delete Article
    //  Auth required
    @DeleteMapping("/{slug}")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //  Add Comments to an Article
    //  Auth required
    @PostMapping("/{slug}/comments")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //    Get Comments from an Article
    //  Auth required
    @GetMapping("/{slug}/comments")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //    Delete Comment
    //  Auth required
    @DeleteMapping("/{slug}/comments/{id}")
    public ResponseEntity<List<Article>> deleteArticle(
            @PathVariable("slug") String slug,
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //    Favorite Article
    //  Auth required
    @PostMapping("/{slug}/favorite")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    //    Unfavorite Article
    //  Auth required
    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }
}