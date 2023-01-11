package com.articles.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtExpiredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public JwtExpiredException(String exception) {
        super(exception);
    }
}

