package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
