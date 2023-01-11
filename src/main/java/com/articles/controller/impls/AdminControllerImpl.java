package com.articles.controller.impls;

import com.articles.controller.AdminController;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import com.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("v1/admin/")
@RestController
public class AdminControllerImpl implements AdminController {

    private final ArticleService articleService;

    @Override
    @GetMapping("article/count")
    public ResponseEntity<Response<ErrorResponse, Long>> countArticlesByWeek() {
        Response<ErrorResponse, Long> articlesCount = articleService.getArticlesCountByWeek();
        return new ResponseEntity<>(articlesCount, HttpStatus.OK);
    }
}
