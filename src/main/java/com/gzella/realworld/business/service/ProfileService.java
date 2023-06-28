package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.persistence.entity.User;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository repository;

    public ProfileResponse getProfile(String username) {
        // get user from repo and map it to profile response todo
        User user = repository.findByUsername(username).orElseThrow();
        return ProfileResponse.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(user.isFollowing())
                .build();
    }

    public ProfileResponse followUser(String username) {
        // get
        return null;
    }

    public ProfileResponse unfollowUser(String username) {
        return null;
    }
}
