package com.articles.controller;


import com.articles.dto.article.ArticleResponse;
import com.articles.dto.article.ArticleRequest;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ArticleController {
    @Operation(summary = "The endpoint for saving a new article", operationId = "new article")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Should fill all fields", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ArticleResponse.class)))
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Article is successfully saved",
                    responseCode = "201",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class))}),

            @ApiResponse(
                    description = "Parameter is invalid",
                    responseCode = "400",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Response<ErrorResponse, ArticleResponse>> add(@Valid @RequestBody ArticleRequest articleRequest, BindingResult result);

    @Operation(summary = "The endpoint for getting a article by Id", operationId = "get article by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class))}
            ),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Response<ErrorResponse, ArticleResponse>> getById(@PathVariable("id") Long id);

    @Operation(summary = "The endpoint for getting whole articles page by page", operationId = "get all articles")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ArticleResponse.class)))}
            ),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Response<ErrorResponse, List<ArticleResponse>>> getAll(@RequestParam(required = false) int pageNumber,
                                                                          @RequestParam int pageSize);
}
