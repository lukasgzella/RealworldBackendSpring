package com.gzella.realworld.business.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArticleQueryParams {
    private String tag;
    private String author;
    private String favorited;
    private int limit;
    private int offset;
}
