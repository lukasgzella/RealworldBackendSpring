package com.gzella.realworld.business.service;

import com.gzella.realworld.business.auxiliary.ArticleResponseMapper;
import com.gzella.realworld.business.auxiliary.IAuthenticationFacade;
import com.gzella.realworld.business.dto.responses.ArticleResponse;
import com.gzella.realworld.business.dto.responses.MultipleArticleResponse;
import com.gzella.realworld.persistence.entity.Article;
import com.gzella.realworld.persistence.entity.Follower;
import com.gzella.realworld.persistence.entity.User;
import com.gzella.realworld.persistence.repository.ArticleRepository;
import com.gzella.realworld.persistence.repository.FollowerRepository;
import com.gzella.realworld.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final ArticleRepository articleRepository;


    public ArticleResponse getArticle(String slug) {
        // get article from art-repo
        Article article = articleRepository.findBySlug(slug);
        return new ArticleResponseMapper().apply(article);
    }


    public MultipleArticleResponse getArticles(String tag, String author, String favorited, int limit, int offset) {
        Page<Article> page = articleRepository.findArticlesByParamsPage(author, tag, favorited, PageRequest.of(offset, limit));
        long articlesCount = articleRepository.countArticlesByParams(author, tag, favorited);
        List<ArticleResponse> articles = page.map(article -> new ArticleResponseMapper().apply(article)).toList();
        return new MultipleArticleResponse(articles, articlesCount);
    }

    public MultipleArticleResponse getArticlesFromFollowedUsers(int limit, int offset) {
        // get followed users.
        User authenticatedUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        Set<Follower> following = authenticatedUser.getFollowing();
        Page<Article> page = articleRepository.findByFollowedUsers(following, PageRequest.of(offset, limit));
        long articlesCount = page.getTotalElements();
        // map to response
        List<ArticleResponse> articles = page.map(article -> new ArticleResponseMapper().apply(article)).toList();
        return new MultipleArticleResponse(articles, articlesCount);
    }
}
