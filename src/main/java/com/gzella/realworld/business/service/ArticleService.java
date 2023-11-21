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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final ArticleRepository articleRepository;


    public ArticleResponse getArticle(String slug) {
        // get article from art-repo
        Article article = articleRepository.findBySlug(slug).orElseThrow();
        User authenticated = checkIfAuthenticated();
        boolean favorited = false;
        boolean following = false;
        if (authenticated != null) {
            favorited = isFavorited(article, authenticated);
            following = isFollowing(authenticated, article.getAuthor());
        }
        article.setFavorited(favorited);
        article.setFollowing(following);
        return new ArticleResponseMapper().apply(article);
    }


    public MultipleArticleResponse getArticles(String tag, String author, String favorited, int limit, int offset) {
        Page<Article> page = articleRepository.findArticlesByParamsPage(author, tag, favorited, PageRequest.of(offset, limit));
        long articlesCount = articleRepository.countArticlesByParams(author, tag, favorited);
        List<ArticleResponse> articles = page.map(article -> new ArticleResponseMapper().apply(article)).toList();
        return new MultipleArticleResponse(articles, articlesCount);
    }

    @Transactional
    public MultipleArticleResponse getArticlesFromFavoritesUsers(int limit, int offset) {
        User authenticatedUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow();
        Set<Follower> following = authenticatedUser.getFollowing();
        Page<Article> page = articleRepository.findArticlesFromFavoritesUsers(String.valueOf(authenticatedUser.getId()), PageRequest.of(offset, limit));
        long articlesCount = articleRepository.countArticlesFromFavoritesUsers(String.valueOf(authenticatedUser.getId()));
        // map to response
        List<ArticleResponse> articles = page.map(article -> new ArticleResponseMapper().apply(article)).toList();
        return new MultipleArticleResponse(articles, articlesCount);
    }

    private User checkIfAuthenticated() {
        String username = authenticationFacade.getAuthentication().getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    private boolean isFavorited(Article article, User authenticated) {
        return authenticated.getFavoriteArticles().contains(article);
    }

    private boolean isFollowing(User userFrom, User userTo) {
        return followerRepository.existsByFromTo(userFrom.getUsername(), userTo.getUsername());
    }

    public ArticleResponse createArticle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current user_id? ");
        String userId = scanner.nextLine();
        User user = userRepository.findById(Long.parseLong(userId));
        System.out.println("title?");
        String title = scanner.nextLine();
        System.out.println("description?");
        String description = scanner.nextLine();
        System.out.println("body?");
        String body = scanner.nextLine();
        System.out.println("taglist? Enter tags separated by spaces");
        List<String> stringList = Arrays.stream(scanner.nextLine().split("\s")).toList();
        // create new article with id
        Article savedArticle = articleRepository.save(Article.builder()
                .author(user)
                .title(title)
                .slug(title.toLowerCase().replace(' ', '-'))
                .description(description)
                .body(body)
                .createdAt(LocalDateTime.now().toString())
                .build());
        // check if there are existing tags with name from stringList in tagRepository
        Set<Tag> existingTags = stringList
                .stream()
                .map(s -> tagRepository.findByName(s)
                        .orElseGet(() -> new Tag(s))).collect(Collectors.toSet());
        // update tags with savedArticle
        existingTags.forEach(tag -> tag.getArticles().add(savedArticle));
        existingTags = existingTags.stream().map(tagRepository::save).collect(Collectors.toSet());

        savedArticle.setTagList(existingTags);
        articleRepository.save(savedArticle);
        ArticleResponse res = new ArticleResponseMapper().apply(savedArticle);
        System.out.println(res);
    }
}
