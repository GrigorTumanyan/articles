package com.articles.controller.impls;

import com.articles.controller.AuthenticationController;
import com.articles.dto.AuthenticationRequestDto;
import com.articles.dto.user.UserRequest;
import com.articles.dto.user.UserResponse;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import com.articles.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("v1/auth/")
@AllArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthService authService;

    @PostMapping("register")
    @Override
    public ResponseEntity<Response<ErrorResponse, UserResponse>> register(UserRequest userRequest) {
        Response<ErrorResponse, UserResponse> register = authService.registration(userRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PostMapping("login")
    @Override
    public ResponseEntity<Response<ErrorResponse, UserResponse>> login(AuthenticationRequestDto requestDto, HttpServletResponse response) {
        Response<ErrorResponse, UserResponse> login = authService.login(requestDto, response);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @GetMapping("activation/{id}")
    @Override
    public ResponseEntity<Response<ErrorResponse, String>> activate(Long id) {
        Response<ErrorResponse, String> response = authService.activateAccount(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
