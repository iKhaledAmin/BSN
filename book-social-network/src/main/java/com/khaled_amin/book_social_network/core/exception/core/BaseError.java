package com.khaled_amin.book_social_network.core.exception.core;

import org.springframework.http.HttpStatus;

public interface BaseError {
    ErrorDomain getDomain();
    ErrorType getType();
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
