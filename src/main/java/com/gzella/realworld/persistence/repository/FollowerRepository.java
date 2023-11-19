package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.Follower;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface    FollowerRepository extends CrudRepository<Follower, Long> {

    @Query("""
            SELECT f FROM Follower f
            WHERE (f.from.username = :userFrom AND f.to.username = :userTo)
            """)
    Optional<Follower> findByFromTo(@Param("userFrom") String userFrom, @Param("userTo") String userTo);

    @Query("""
            SELECT CASE WHEN COUNT(f)> 0 THEN TRUE ELSE FALSE END FROM Follower f
            WHERE (f.from.username = :userFrom AND f.to.username = :userTo)
            """)
    boolean existsByFromTo(@Param("userFrom") String userFrom, @Param("userTo") String userTo);
}
