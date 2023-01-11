package com.articles.service;

import com.articles.dto.article.ArticleResponse;
import com.articles.dto.article.ArticleRequest;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;

import java.util.List;

public interface ArticleService {

    Response<ErrorResponse, ArticleResponse> add(ArticleRequest articleRequest);

    Response<ErrorResponse, ArticleResponse> getById(Long id);

    Response<ErrorResponse, List<ArticleResponse>> getAll(int pageNumber, int pageSize);

    Response<ErrorResponse, Long> getArticlesCountByWeek();
}
