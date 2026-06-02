package com.khaled_amin.book_social_network.core.exception.security;

import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;

public interface SecurityError extends BaseError {
    default ErrorType getType() {
        return ErrorType.SECURITY;
    }
}
