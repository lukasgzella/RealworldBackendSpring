package com.gzella.realworld.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
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
    private String createdAt;
    private String updatedAt;
    private boolean favorited;
    private long favoritesCount;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;
    @Builder.Default
    @ManyToMany(mappedBy = "favoriteArticles")
    private Set<User> followingUsers = new HashSet<>();
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;
    @OneToMany(mappedBy = "article")
    private Set<Tag> tagList;

}