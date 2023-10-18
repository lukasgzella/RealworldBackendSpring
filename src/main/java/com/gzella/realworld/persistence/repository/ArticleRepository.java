package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query("""
            SELECT a FROM Article a 
            LEFT JOIN FETCH a.comments
            WHERE (a.id = :id) 
            """)
    Article findById(@Param("id") long id);

    @Query("""
            SELECT a FROM Article a 
            LEFT JOIN FETCH a.author 
            LEFT JOIN FETCH a.tagList 
            WHERE a.author.username = :author 
            """)
    List<Article> findArticlesByAuthor(@Param("author") String author);

    @Query("""
            SELECT a FROM Article a
            WHERE (:tag IN (SELECT t.name FROM a.tagList t))
            """)
    List<Article> findArticlesByTag(@Param("tag") String tag);

    @Query("""
            SELECT a FROM Article a
            WHERE (:favorited IN (SELECT fu.username FROM a.followingUsers fu))
            """)
    List<Article> findArticlesByFavorited(@Param("favorited") String favorited);

    @Query("""
            SELECT a FROM Article a
            WHERE (:author IS NULL OR a.author.username = :author)
            AND (:tag IS NULL OR :tag IN (SELECT t.name FROM a.tagList t))
            AND (:favorited IS NULL OR :favorited IN (SELECT fu.username FROM a.followingUsers fu))
            """)
    List<Article> findArticlesByParams(
            @Param("author") String author,
            @Param("tag") String tag,
            @Param("favorited") String favorited
    );
    @Query("""
            SELECT a FROM Article a
            LEFT JOIN FETCH a.tagList
            LEFT JOIN FETCH a.followingUsers
            WHERE (:author IS NULL OR a.author.username = :author)
            AND (:tag IS NULL OR :tag IN (SELECT t.name FROM a.tagList t))
            AND (:favorited IS NULL OR :favorited IN (SELECT fu.username FROM a.followingUsers fu))
            """)
    Page<Article> findArticlesByParamsPage(
            @Param("author") String author,
            @Param("tag") String tag,
            @Param("favorited") String favorited,
            Pageable pageable
    );
    @Query("""
            SELECT COUNT(*) a FROM Article a
            WHERE (:author IS NULL OR a.author.username = :author)
            AND (:tag IS NULL OR :tag IN (SELECT t.name FROM a.tagList t))
            AND (:favorited IS NULL OR :favorited IN (SELECT fu.username FROM a.followingUsers fu))
            """)
    long countArticlesByParams(
            @Param("author") String author,
            @Param("tag") String tag,
            @Param("favorited") String favorited
    );
    @Query("""
            SELECT a FROM Article a
            WHERE a.author.id IN (SELECT f.to.id FROM Follower f WHERE f.from.id = :user_id)
            """)
    Page <Article> findByFollowingUser(@Param("user_id") String user_id, Pageable pageable);

    Optional<Article> findBySlug(String slug);

    @Query("""
            SELECT a FROM Article a 
            LEFT JOIN FETCH a.comments
            WHERE (a.slug = :slug) 
            """)
    Optional<Article> findBySlugWithComments(@Param("slug") String slug);

}
