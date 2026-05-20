package com.khaled_amin.book_social_network.core.exception;

import com.khaled_amin.book_social_network.core.exception.core.BaseError;
import org.springframework.http.HttpStatus;

public interface BusinessError extends BaseError {

    HttpStatus getStatus();

}