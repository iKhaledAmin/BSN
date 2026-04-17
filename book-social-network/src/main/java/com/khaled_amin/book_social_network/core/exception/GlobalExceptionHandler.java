package com.khaled_amin.book_social_network.core.exception;


import com.khaled_amin.book_social_network.core.api.ApiError;
import com.khaled_amin.book_social_network.core.api.ErrorResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleApiException(BaseException ex, HttpServletRequest request) {

        var error = ex.getError();

        ApiError apiError = ApiError.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .path(request.getRequestURI())
                .build();

//        // todo later
//        // Log internal debug info
//        log.error("Error occurred: value={}, debug={}",
//                error.getCode(),
//                ex.getDebugDetails()
//        );

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(apiError));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, List<String>> validationDetails = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> {
                    validationDetails
                            .computeIfAbsent(toSnakeCase(err.getField()), k -> new ArrayList<>())
                            .add(err.getDefaultMessage());
                });

        CommonError commonError = CommonError.VALIDATION_ERROR;

        ApiError error = ApiError.builder()
                .status(commonError.getStatus().value())
                .code(commonError.getCode())
                .message(commonError.getMessage())
                .details(validationDetails)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(commonError.getStatus())
                .body(ApiResponseFactory.error(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request) {

//        // TODO replace with logger
//        System.err.println("ERROR_ID=" + requestId);
//        ex.printStackTrace();

        CommonError commonError = CommonError.INTERNAL_ERROR;

        ApiError error = ApiError.builder()
                .status(commonError.getStatus().value())
                .code(commonError.getCode())
                .message(commonError.getMessage())
//                .details(Map.of(
//                        "requestId", requestId
//                ))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(commonError.getStatus())
                .body(ApiResponseFactory.error(error));
    }


    private String toSnakeCase(String input) {
        return input
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}