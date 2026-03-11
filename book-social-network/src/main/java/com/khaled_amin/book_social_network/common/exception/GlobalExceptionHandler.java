package com.khaled_amin.book_social_network.common.exception;


import com.khaled_amin.book_social_network.common.dto.ApiError;
import com.khaled_amin.book_social_network.common.dto.ErrorResponse;
import com.khaled_amin.book_social_network.common.response.ApiResponseFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code("ROLE_NOT_FOUND")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseFactory.error(error));
    }
}