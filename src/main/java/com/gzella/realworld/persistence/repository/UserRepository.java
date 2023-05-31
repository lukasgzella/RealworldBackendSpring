package com.gzella.realworld.persistence.repository;

import com.gzella.realworld.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
