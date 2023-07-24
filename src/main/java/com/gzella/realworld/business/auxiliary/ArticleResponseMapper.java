package com.gzella.realworld.business.auxiliary;

import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.persistence.entity.Article;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class ArticleResponseMapper implements Function<Article, ArticleResponse> {
    @Override
    public ArticleResponse apply(Article article) {
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
