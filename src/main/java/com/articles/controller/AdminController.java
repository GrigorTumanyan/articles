package com.articles.controller;

import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import org.springframework.http.ResponseEntity;

public interface AdminController {
    ResponseEntity<Response<ErrorResponse, Long>> countArticlesByWeek();
}
