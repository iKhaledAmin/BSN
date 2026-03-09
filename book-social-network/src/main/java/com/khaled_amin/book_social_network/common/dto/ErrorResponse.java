package com.khaled_amin.book_social_network.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponse {

    private Meta meta;

    private ApiError error;

}