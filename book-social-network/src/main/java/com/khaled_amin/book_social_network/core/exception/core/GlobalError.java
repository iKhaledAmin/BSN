package com.khaled_amin.book_social_network.core.exception.core;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum GlobalError implements BusinessError {

    NO_CONTENT(
            "CORE_GLOBAL_EXCEPTION_NO_CONTENT",
            HttpStatus.NO_CONTENT,
            "No content"
    ),

    NO_CODE(
            "CORE_GLOBAL_EXCEPTION_NO_CODE",
            HttpStatus.BAD_REQUEST,
            "No code"
    ),

    // -------------------- VALIDATION -------------------- //
    VALIDATION_ERROR(
            "CORE_GLOBAL_EXCEPTION_VALIDATION_ERROR",
            HttpStatus.BAD_REQUEST,
            "Validation failed"
    ),

    // -------------------- INTERNAL -------------------- //
    INTERNAL_SERVER_ERROR(
            "CORE_GLOBAL_EXCEPTION_INTERNAL_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unexpected internalServer server error"
    ),

    // -------------------- GENERIC -------------------- //
    RESOURCE_NOT_FOUND(
            "CORE_GLOBAL_EXCEPTION_RESOURCE_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Resource not found"
    ),

    CONFLICT(
            "CORE_GLOBAL_EXCEPTION_CONFLICT",
            HttpStatus.CONFLICT,
            "Conflict occurred"
    ),

    FORBIDDEN(
            "CORE_GLOBAL_EXCEPTION_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Access denied"
    );

    private final String code;
    private final HttpStatus status;
    private final String message;

    GlobalError(String code, HttpStatus status, String message) {
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