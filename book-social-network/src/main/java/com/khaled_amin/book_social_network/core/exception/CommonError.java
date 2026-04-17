package com.khaled_amin.book_social_network.core.exception;

import org.springframework.http.HttpStatus;

public enum CommonError implements BaseError {

    // -------------------- VALIDATION -------------------- //
    VALIDATION_ERROR(
            "COMMON_VALIDATION_ERROR",
            HttpStatus.BAD_REQUEST,
            "Validation failed"
    ),

    // -------------------- INTERNAL -------------------- //
    INTERNAL_ERROR(
            "COMMON_INTERNAL_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unexpected error occurred"
    ),

    // -------------------- GENERIC -------------------- //
    RESOURCE_NOT_FOUND(
            "COMMON_RESOURCE_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Resource not found"
    ),

    CONFLICT(
            "COMMON_CONFLICT",
            HttpStatus.CONFLICT,
            "Conflict occurred"
    ),

    FORBIDDEN(
            "COMMON_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Access denied"
    );

    private final String code;
    private final HttpStatus status;
    private final String message;

    CommonError(String code, HttpStatus status, String message) {
      this.code = code;
      this.status = status;
      this.message = message;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public HttpStatus getStatus() {
      return status;
    }

    @Override
    public String getMessage() {
      return message;
    }
}