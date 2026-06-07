package com.khaled_amin.book_social_network.core.exception.core;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import org.springframework.http.HttpStatus;

public interface BaseError {
    SystemDomain getDomain();
    ErrorType getType();
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
