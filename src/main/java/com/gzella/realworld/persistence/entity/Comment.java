package com.gzella.realworld.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;
    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    private String createdAt;
    private String updatedAt;
    private String body;

}