package com.khaled_amin.book_social_network.common.error;

import org.springframework.http.HttpStatus;

public interface ApiErrorCode {

    HttpStatus getStatus();

    String getMessage();

    String name();
}