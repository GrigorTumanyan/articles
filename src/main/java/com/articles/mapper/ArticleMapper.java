package com.articles.mapper;

import com.articles.dto.article.ArticleRequest;
import com.articles.dto.article.ArticleResponse;
import com.articles.model.Article;

public class ArticleMapper {

    private ArticleMapper() {

    }

    public static Article articleRequestToArticle(ArticleRequest articleRequest) {
        return Article.builder()
                .title(articleRequest.getTitle())
                .author(articleRequest.getAuthor())
                .content(articleRequest.getContent())
                .publishDate(articleRequest.getPublishDate())
                .build();
    }

    public static ArticleResponse articleToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .content(article.getContent())
                .publishDate(article.getPublishDate())
                .build();
    }
}
