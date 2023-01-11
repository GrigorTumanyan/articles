package com.articles.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String errorCode;
    private String errorStatus;
    private String errorMessage;
    private LocalDateTime localDateTime;

    public ErrorResponse(String errorCode, String errorStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.localDateTime = LocalDateTime.now();
    }
}

