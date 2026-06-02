package com.khaled_amin.book_social_network.core.exception.core;


import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;
import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.business.BusinessException;
import com.khaled_amin.book_social_network.core.exception.technical.GlobalTechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.exception.validation.GlobalValidationError;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;
import com.khaled_amin.book_social_network.security.exception.AuthorizationError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {

        BusinessError error = ex.getError();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(ex.getMessage())
                .details(ex.getClientDetails())
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

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ApiErrorResponse> handleTechnicalException(TechnicalException ex, HttpServletRequest request) {

        TechnicalError error = GlobalTechnicalError.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(ex.getError().getCode())
                .message(ex.getMessage())
                .details(Map.of())
                .path(request.getRequestURI())
                .build();

        // TODO logging later
        // log.error("Technical error", ex);

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(errorResponse));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex, HttpServletRequest request){

        int httpStatus = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(httpStatus)
                .code(ex.getError().getCode())
                .message(ex.getMessage())
                .details(ex.getClientDetails())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(
                        ApiResponseFactory.error(errorResponse)
                );
    }


    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiErrorResponse> handleSecurityException(SecurityException ex, HttpServletRequest request) {

        SecurityError error = ex.getError();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(ex.getMessage())
                .details(ex.getClientDetails())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(errorResponse));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Set<String>> validationDetails = new LinkedHashMap<>();
        ValidationError error = GlobalValidationError.VALIDATION_ERROR;

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> {
                    validationDetails
                            .computeIfAbsent(toSnakeCase(err.getField()), k -> new HashSet<>())
                            .add(err.getDefaultMessage());
                });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(error.getMessage())
                .details(validationDetails)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(errorResponse));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        AuthorizationError error = AuthorizationError.ACCESS_DENIED;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(error.getMessage())
                .details(Map.of())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(errorResponse));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {

//        // TODO replace with logger
//        System.err.println("ERROR_ID=" + requestId);
//        ex.printStackTrace();

        TechnicalError error = GlobalTechnicalError.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                //.message(error.getMessage())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseFactory.error(errorResponse));
    }


    private String toSnakeCase(String input) {
        return input
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}