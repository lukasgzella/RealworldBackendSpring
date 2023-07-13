package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.ArticleQueryParams;
import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.business.dto.responses.MultipleArticleResponse;
import com.gzella.realworld.persistence.entity.Article;
import com.gzella.realworld.persistence.repository.ArticleRepository;
import com.gzella.realworld.persistence.repository.FollowerRepository;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public MultipleArticleResponse getArticles(String tag, String author, String favorited, int limit, int offset) {
        Page<Article> page = articleRepository.findByParams(tag, author, favorited, PageRequest.of(offset, limit));
        long articlesCount = page.getTotalElements();
        // map to response

        return new MultipleArticleResponse(articles, articlesCount);
    }
}
