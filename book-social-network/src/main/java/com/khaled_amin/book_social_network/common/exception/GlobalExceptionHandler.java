package com.khaled_amin.book_social_network.common.exception;


import com.khaled_amin.book_social_network.common.dto.ApiError;
import com.khaled_amin.book_social_network.common.dto.ErrorResponse;
import com.khaled_amin.book_social_network.common.factory.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            BaseApiException ex,
            HttpServletRequest request) {

        var code = ex.getErrorCode();

        ApiError error = ApiError.builder()
                .status(code.getStatus().value())
                .code(code.name())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponseFactory.error(error));
    }
}