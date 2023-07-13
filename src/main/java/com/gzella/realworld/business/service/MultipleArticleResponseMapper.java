package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.persistence.entity.Article;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class MultipleArticleResponseMapper implements Function<Page<Article>, List<ArticleResponse>> {
    @Override
    public List<ArticleResponse> apply(Page<Article> articles) {
        return List.of();
    }
}
