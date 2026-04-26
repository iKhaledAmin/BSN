package com.khaled_amin.book_social_network.core.exception;

import org.springframework.http.HttpStatus;

public interface BaseError {

    String getCode();

    HttpStatus getStatus();

    String getMessage();

}