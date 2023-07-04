package com.gzella.realworld.business.dto.responses;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.gzella.realworld.persistence.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("article")
public class ArticleResponse {

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
