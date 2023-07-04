package com.gzella.realworld.business.service;

import com.gzella.realworld.persistence.repository.FollowerRepository;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;



}
