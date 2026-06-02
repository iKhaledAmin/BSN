package com.khaled_amin.book_social_network.core.exception.business;

import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;

public interface BusinessError extends BaseError {

    default ErrorType getType() {
        return ErrorType.BUSINESS;
    }
}