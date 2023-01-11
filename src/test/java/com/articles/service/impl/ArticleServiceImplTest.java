package com.articles.service.impl;

import com.articles.dto.article.ArticleRequest;
import com.articles.dto.article.ArticleResponse;
import com.articles.exception.RecordNotFoundException;
import com.articles.mapper.ArticleMapper;
import com.articles.model.Article;
import com.articles.repository.ArticleRepository;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article article;

    private ArticleRequest articleRequest;

    private ArticleResponse articleExpectedResponse;

    private Page<Article> articlePageable;

    private List<ArticleResponse> articlesExpectedResponse;


    private final LocalDateTime currentTime = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        article = new Article(1L, "first", "Me", "Something", currentTime);
        articleExpectedResponse = ArticleMapper.articleToArticleResponse(article);
        articleRequest = new ArticleRequest("first", "Me", "Something", currentTime);
        articlePageable = new PageImpl<>(List.of(article));
        articlesExpectedResponse = List.of(articleExpectedResponse);
    }

    @Test
    void add() {
        when(articleRepository.save(any())).thenReturn(article);
        Response<ErrorResponse, ArticleResponse> savedArticle = articleService.add(articleRequest);
        assertEquals(articleExpectedResponse, savedArticle.getSuccessObject());
    }

    @Test
    void getById() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        Response<ErrorResponse, ArticleResponse> byId = articleService.getById(1L);
        assertEquals(articleExpectedResponse, byId.getSuccessObject());
    }

    @Test
    void getByIdNegativeCase() {
        when(articleRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> articleService.getById(200L));
    }

    @Test
    void getAll() {
        when(articleRepository.findAll(PageRequest.of(0, 5))).thenReturn(articlePageable);
        Response<ErrorResponse, List<ArticleResponse>> all = articleService.getAll(0, 5);
        assertEquals(articlesExpectedResponse, all.getSuccessObject());
    }

    @Test
    void getAllNegativeCase() {
        when(articleRepository.findAll(PageRequest.of(10, 30))).thenReturn(new PageImpl<>(List.of()));
        assertThrows(RecordNotFoundException.class, () -> articleService.getAll(10, 30));
    }

    @Test
    void getArticlesCountByWeek() {
        when(articleRepository.findArticleCountByWeek()).thenReturn(Optional.of(5L));
        Response<ErrorResponse, Long> articlesCount = articleService.getArticlesCountByWeek();
        assertEquals(5L, articlesCount.getSuccessObject());
    }

    @Test
    void getArticlesCountByWeekNegativeCase() {
        when(articleRepository.findArticleCountByWeek()).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, articleService::getArticlesCountByWeek);
    }
}