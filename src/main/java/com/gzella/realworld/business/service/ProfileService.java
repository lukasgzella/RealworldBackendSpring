package com.gzella.realworld.business.service;

import com.gzella.realworld.business.auxiliary.IAuthenticationFacade;
import com.gzella.realworld.business.dto.responses.ProfileResponse;
import com.gzella.realworld.persistence.entity.Follower;
import com.gzella.realworld.persistence.entity.User;
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
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    @Transactional
    public ProfileResponse getProfile(String username) {
        // todo exception no such user
        User userTo = userRepository.findByUsername(username).orElseThrow();
        String usernameFrom = authenticationFacade.getAuthentication().getName();
        boolean isFollowing = false;
        if (usernameFrom != null) {
            isFollowing = followerRepository.existsByFromTo(usernameFrom, username);
        }
        return ProfileResponse.builder()
                .username(userTo.getUsername())
                .bio(userTo.getBio())
                .image(userTo.getImage())
                .following(isFollowing)
                .build();
    }

    @Transactional
    public ProfileResponse followUser(String username) {
        User userTo = userRepository.findByUsername(username).orElseThrow();
        ProfileResponse res = ProfileResponse.builder()
                .username(userTo.getUsername())
                .bio(userTo.getBio())
                .image(userTo.getImage())
                .following(true)
                .build();
        String usernameFrom = authenticationFacade.getAuthentication().getName();
        boolean isFollowing = followerRepository.existsByFromTo(usernameFrom, username);
        if (isFollowing) {
            return res;
        }
        User userFrom = userRepository.findByUsername(usernameFrom).orElseThrow();
        Follower follower = new Follower(userFrom, userTo);
        follower = followerRepository.save(follower);
        userTo.getFollowers().add(follower);
        userFrom.getFollowing().add(follower);
        userRepository.save(userTo);
        userRepository.save(userFrom);
        return res;
    }

    @Transactional
    public ProfileResponse unfollowUser(String username) {
        User userTo = userRepository.findByUsername(username).orElseThrow();
        ProfileResponse res = ProfileResponse.builder()
                .username(userTo.getUsername())
                .bio(userTo.getBio())
                .image(userTo.getImage())
                .following(false)
                .build();
        String usernameFrom = authenticationFacade.getAuthentication().getName();
        boolean isFollowing = followerRepository.existsByFromTo(usernameFrom, username);
        if (!isFollowing) {
            return res;
        }
        User userFrom = userRepository.findByUsername(usernameFrom).orElseThrow();
        Follower follower = followerRepository.findByFromTo(usernameFrom, username).get();
        userFrom.getFollowing().remove(follower);
        userTo.getFollowers().remove(follower);
        followerRepository.delete(follower);
        userRepository.save(userTo);
        userRepository.save(userFrom);
        return res;
    }
}
