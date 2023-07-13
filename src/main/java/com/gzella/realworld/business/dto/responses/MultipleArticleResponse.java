package com.gzella.realworld.business.dto.responses;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MultipleArticleResponse {

    private List<ArticleResponse> articles;
    private int articlesCount;

}
