package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.persistence.entity.Follower;
import com.gzella.realworld.persistence.repository.FollowerRepository;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    public ProfileResponse getProfile(String username) {
        // get user from repo and map it to profile response todo
        User previewUser = userRepository.findByUsername(username).orElseThrow();
        User authenticatedUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        boolean isFollowing = authenticatedUser.getFollowing().stream()
                .anyMatch(f -> f.getTo().getUsername().equals(username));
        return ProfileResponse.builder()
                .username(previewUser.getUsername())
                .bio(previewUser.getBio())
                .image(previewUser.getImage())
                .following(isFollowing)
                .build();
    }

    @Transactional
    public ProfileResponse followUser(String username) {
        // get user from repo, get authenticated user from repo, add follower to followers list, add follower
        // to following list. all should be in transaction, return ProfileRes.
        // check if already follow todo (changed list to set)
        User previewUser = userRepository.findByUsername(username).orElseThrow();
        User authenticatedUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        Follower follower = new Follower(authenticatedUser, previewUser);
        follower = followerRepository.save(follower);
        previewUser.getFollowers().add(follower);
        authenticatedUser.getFollowing().add(follower);
        userRepository.save(previewUser);
        userRepository.save(authenticatedUser);
        return ProfileResponse.builder()
                .username(previewUser.getUsername())
                .bio(previewUser.getBio())
                .image(previewUser.getImage())
                .following(true)
                .build();
    }

    @Transactional
    public ProfileResponse unfollowUser(String username) {
        // get user from repo, get authenticated user from repo, remove follower from followers set, rem follower
        // from following set. all should be in transaction, return ProfileRes.
        User previewUser = userRepository.findByUsername(username).orElseThrow();
        User authenticatedUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        Follower follower = authenticatedUser.getFollowing().stream()
                .filter(f -> f.getTo().getUsername().equals(username))
                .findFirst()
                .get(); // todo handle NPE
        authenticatedUser.getFollowing().remove(follower);
        previewUser.getFollowers().remove(follower);
        followerRepository.delete(follower);
        userRepository.save(previewUser);
        userRepository.save(authenticatedUser);
        return ProfileResponse.builder()
                .username(previewUser.getUsername())
                .bio(previewUser.getBio())
                .image(previewUser.getImage())
                .following(false)
                .build();
    }
}
