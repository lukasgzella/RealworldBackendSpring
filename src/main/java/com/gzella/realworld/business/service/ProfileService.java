package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.persistence.entity.Follower;
import com.gzella.realworld.persistence.entity.User;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    private final UserRepository repository;

    public ProfileResponse getProfile(String username) {
        // get user from repo and map it to profile response todo
        User user = repository.findByUsername(username).orElseThrow();
        return ProfileResponse.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(!user.getFollowing().isEmpty())
                .build();
    }

    public ProfileResponse followUser(String username) {
        // get user from repo, get authenticated user from repo, add follower to followers list, add follower
        // to following list. all should be in transaction
        User previewUser = repository.findByUsername(username).orElseThrow();
        User authenticatedUser = repository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        previewUser.getFollowers().add(Follower);
        authenticatedUser.getFollowing().add(Follower);
        return null;
    }

    public ProfileResponse unfollowUser(String username) {
        return null;
    }
}
