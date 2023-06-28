package com.gzella.realworld.persistence.entity;

import java.util.List;

public class Article {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private String createdAt;
    private String updatedAt;
    private boolean favorited;
    private String favoritesCount;
    private Author author;

}
