package com.articles.controller;

import com.articles.dto.AuthenticationRequestDto;
import com.articles.dto.user.UserResponse;
import com.articles.dto.user.UserRequest;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationController {

    ResponseEntity<Response<ErrorResponse, UserResponse>> register(@RequestBody UserRequest userRequest);

    ResponseEntity<Response<ErrorResponse, UserResponse>> login(@RequestBody AuthenticationRequestDto requestDto, HttpServletResponse response);

    ResponseEntity<Response<ErrorResponse, String>> activate(@PathVariable Long id);


}
