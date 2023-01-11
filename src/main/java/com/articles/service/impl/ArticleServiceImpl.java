package com.articles.service.impl;

import com.articles.dto.article.ArticleRequest;
import com.articles.dto.article.ArticleResponse;
import com.articles.exception.RecordNotFoundException;
import com.articles.mapper.ArticleMapper;
import com.articles.model.Article;
import com.articles.repository.ArticleRepository;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import com.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private static final String ARTICLE_NOT_FOUND = "Article is not found";

    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public Response<ErrorResponse, ArticleResponse> add(ArticleRequest articleRequest) {
        Article article = ArticleMapper.articleRequestToArticle(articleRequest);
        Article savedArticle = articleRepository.save(article);
        return new Response<>(null, ArticleMapper.articleToArticleResponse(savedArticle));
    }

    @Override
    public Response<ErrorResponse, ArticleResponse> getById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ARTICLE_NOT_FOUND));
        return new Response<>(null, ArticleMapper.articleToArticleResponse(article));
    }

    @Override
    public Response<ErrorResponse, List<ArticleResponse>> getAll(int pageNumber, int pageSize) {
        Page<Article> articles = articleRepository.findAll(PageRequest.of(pageNumber, pageSize));
        if (articles.isEmpty()) throw new RecordNotFoundException(ARTICLE_NOT_FOUND);
        List<ArticleResponse> articleResponseList = articles.stream().map(ArticleMapper::articleToArticleResponse).toList();
        return new Response<>(null, articleResponseList);
    }

    @Override
    public Response<ErrorResponse, Long> getArticlesCountByWeek() {
        Long count = articleRepository.findArticleCountByWeek().orElseThrow(() -> new RecordNotFoundException(ARTICLE_NOT_FOUND));
        return new Response<>(null, count);
    }

}
