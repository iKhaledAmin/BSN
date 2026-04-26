package com.khaled_amin.book_social_network.core.exception;


import com.khaled_amin.book_social_network.core.api.ErrorResponse;
import com.khaled_amin.book_social_network.core.api.ApiErrorResponse;
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
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BaseException ex, HttpServletRequest request) {

        BaseError error = ex.getError();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .path(request.getRequestURI())
                .build();

//        // todo later
//        // Log internalServer debug info
//        log.error("Error occurred: value={}, debug={}",
//                error.getCode(),
//                ex.getDebugDetails()
//        );

        return ResponseEntity
                .status(error.getStatus())
                .body(
                        ApiResponseFactory.error(errorResponse)
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Set<String>> validationDetails = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> {
                    validationDetails
                            .computeIfAbsent(toSnakeCase(err.getField()), k -> new HashSet<>())
                            .add(err.getDefaultMessage());
                });

        GlobalError globalError = GlobalError.VALIDATION_ERROR;

        ErrorResponse error = ErrorResponse.builder()
                .status(globalError.getStatus().value())
                .code(globalError.getCode())
                .message(globalError.getMessage())
                .details(validationDetails)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(globalError.getStatus())
                .body(ApiResponseFactory.error(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {

//        // TODO replace with logger
//        System.err.println("ERROR_ID=" + requestId);
//        ex.printStackTrace();

        GlobalError globalError = GlobalError.INTERNAL_SERVER_ERROR;

        ErrorResponse error = ErrorResponse.builder()
                .status(globalError.getStatus().value())
                .code(globalError.getCode())
                .message(ex.getMessage())
//                .details(Map.of(
//                        "requestId", requestId
//                ))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(globalError.getStatus())
                .body(ApiResponseFactory.error(error));
    }


    private String toSnakeCase(String input) {
        return input
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}