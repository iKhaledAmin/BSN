package com.khaled_amin.book_social_network.core.exception.technical;


import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorType;
import org.springframework.http.HttpStatus;

public interface TechnicalError extends BaseError {

    default HttpStatus getStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    default ErrorType getType() {
        return ErrorType.TECHNICAL;
    }

}
