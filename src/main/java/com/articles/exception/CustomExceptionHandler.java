package com.articles.exception;

import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse("500", "ServerException", ex.getMessage());
        Response<ErrorResponse, ?> response = new Response<>(error, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleUserNotFoundException(RecordNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("404", "NOT_FOUND", ex.getLocalizedMessage());
        Response<ErrorResponse, ?> response = new Response<>(error, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtExpiredException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleJwtExpiredException(JwtExpiredException ex) {
        ErrorResponse errorResponse = new ErrorResponse("403", "FORBIDDEN", ex.getLocalizedMessage());
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleConflictException(ConflictException exception) {
        ErrorResponse conflict = new ErrorResponse("409", "CONFLICT", exception.getMessage());
        Response<ErrorResponse, ?> errorResponse = new Response<>(conflict, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleBadRequestException(BadRequestException exception) {
        ErrorResponse badRequest = new ErrorResponse("400", "BAD_REQUEST", exception.getMessage());
        Response<ErrorResponse, ?> errorResponse = new Response<>(badRequest, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
