package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.business.dto.ArticleQueryParams;
import com.gzella.realworld.persistence.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query(
            """
                    SELECT a FROM Article a
                    WHERE (:tag IS NULL OR :tag IN (SELECT t.tag.name FROM a.includeTags t))
                    AND (:author IS NULL OR a.author.username = :author)
                    AND (:favorited IS NULL OR :favorited IN (SELECT fu.user.username FROM a.favoriteUsers fu))
                    ORDER BY a.createdAt DESC
                    """)
    Page<Article> findByParams(
            @Param("tag") String tag,
            @Param("author") String author,
            @Param("favorited") String favorited,
            Pageable pageable
    );
    Article findBySlug(String slug);
}
