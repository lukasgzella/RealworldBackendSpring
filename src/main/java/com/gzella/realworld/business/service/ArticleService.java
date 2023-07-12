package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.persistence.entity.Article;
import com.gzella.realworld.persistence.repository.ArticleRepository;
import com.gzella.realworld.persistence.repository.FollowerRepository;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final ArticleRepository articleRepository;

    public ArticleResponse getArticle(String slug) {
        // get article from art-repo
        Article article = articleRepository.findBySlug(slug);
        return ArticleResponse.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTagList())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(article.isFavorited())
                .favoritesCount(article.getFavoritesCount())
                .author(article.getAuthor())
                .build();
    }


}
