package com.khaled_amin.book_social_network.core.exception.policy;

import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;
import org.springframework.http.HttpStatus;

public interface PolicyError extends BaseError {

    default HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    default ErrorType getType() {
        return ErrorType.POLICY;
    }

}