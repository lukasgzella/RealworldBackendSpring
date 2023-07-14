package com.gzella.realworld.persistence.entity;

import com.gzella.realworld.business.dto.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private String createdAt;
    private String updatedAt;
    private boolean favorited;
    private String favoritesCount;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User author;

//    todo make parallel hibernate app to illustrate coupling between entities and test it how it works!

}
