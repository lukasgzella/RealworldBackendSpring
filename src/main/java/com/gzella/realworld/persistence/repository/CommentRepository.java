package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Long> {
}
