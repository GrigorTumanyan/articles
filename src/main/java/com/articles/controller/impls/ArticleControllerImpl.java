package com.articles.controller.impls;

import com.articles.controller.ArticleController;
import com.articles.dto.article.ArticleRequest;
import com.articles.dto.article.ArticleResponse;
import com.articles.exception.BadRequestException;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import com.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("v1/article/")
@RequiredArgsConstructor
public class ArticleControllerImpl implements ArticleController {

    private final ArticleService articleService;


    @Override
    @PostMapping("")
    public ResponseEntity<Response<ErrorResponse, ArticleResponse>> add(ArticleRequest articleRequest, BindingResult result) {
        if (result.hasErrors()) {
            String fieldName = result.getFieldError().getField();
            String cause = result.getFieldError().getDefaultMessage();
            throw new BadRequestException("Field: " + fieldName + cause);
        }
        Response<ErrorResponse, ArticleResponse> created = articleService.add(articleRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<Response<ErrorResponse, ArticleResponse>> getById(Long id) {
        Response<ErrorResponse, ArticleResponse> article = articleService.getById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<Response<ErrorResponse, List<ArticleResponse>>> getAll(int pageNumber, int pageSize) {
        Response<ErrorResponse, List<ArticleResponse>> articles = articleService.getAll(pageNumber, pageSize);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}