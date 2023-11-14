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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @Builder.Default
    @ManyToMany(mappedBy = "favoriteArticles")
    private Set<User> followingUsers = new HashSet<>();

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "articles_tag-list",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tagList = new HashSet<>();

    private String description;
    private String body;

    private String createdAt;
    private String updatedAt;

    private String title;
    private String slug;

    private boolean favorited = false;
    private boolean following = false;

    @Builder
    public Article(Long id, User author, Set<User> followingUsers, List<Comment> comments, Set<Tag> tagList, String title) {
        this.id = id;
        this.author = author;
        this.followingUsers = followingUsers;
        this.comments = comments;
        this.tagList = tagList;
        this.title = title;
        this.slug = title.toLowerCase().replace(' ','-');
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", author=" + author.getUsername() +
                ", comments=" + comments +
                ", followingUsers=" + followingUsers.stream().map(User::getUsername).toList() +
                ", favoritesCount=" + followingUsers.size() +
                ", tagList" + tagList.stream().map(Tag::getName).toList() +
                '}';
    }
}