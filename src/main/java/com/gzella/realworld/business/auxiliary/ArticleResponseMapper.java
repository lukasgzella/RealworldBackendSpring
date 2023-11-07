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
                .tagList(article.getTagList().stream().map(Tag::getName).toList())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(article.isFavorited())
                .favoritesCount(article.getFollowingUsers().size()) // followingUsers Hashset.size()
                .author(new Author(
                        article.getAuthor().getUsername(),
                        article.getAuthor().getBio(),
                        article.getAuthor().getImage(),
                        article.isFollowing()
                ))
                .build();
    }
}