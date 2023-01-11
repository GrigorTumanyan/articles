package com.articles.service;

import com.articles.dto.AuthenticationRequestDto;
import com.articles.dto.user.UserResponse;
import com.articles.dto.user.UserRequest;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    Response<ErrorResponse, UserResponse> login(AuthenticationRequestDto requestDto, HttpServletResponse response);

    Response<ErrorResponse, UserResponse> registration(UserRequest userRequest);

    Response<ErrorResponse, String> activateAccount(Long id);
}
