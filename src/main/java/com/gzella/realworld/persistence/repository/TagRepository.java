package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    //    Optional<Tag> findByName(String name);
    @Query("""
            SELECT t FROM Tag t
            LEFT JOIN FETCH t.articles
            WHERE t.name = :name
            """)
    Optional<Tag> findByName(@Param("name") String name);
}