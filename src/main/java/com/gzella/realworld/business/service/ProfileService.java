package com.gzella.realworld.business.service;

import com.gzella.realworld.business.dto.responses.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    public ProfileResponse getProfile(String username) {
        // get user from repo and map it to profile response todo
        return null;
    }

    public ProfileResponse followUser(String username) {
        return null;
    }

    public ProfileResponse unfollowUser(String username) {
        return null;
    }
}
