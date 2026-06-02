package com.khaled_amin.book_social_network.core.exception.validation;

import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;
import org.springframework.http.HttpStatus;

public interface ValidationError extends BaseError {

    default HttpStatus getStatus(){
        return HttpStatus.BAD_REQUEST;
    }

    default ErrorType getType() {
        return ErrorType.VALIDATION;
    }

}
