package com.gzella.realworld.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

}
