package com.khaled_amin.book_social_network.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.khaled_amin.book_social_network.core.utils.SnakeCaseMapSerializer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;


@Getter
@SuperBuilder
public class ApiError {


    @JsonProperty("status")
    private int status;

    @JsonProperty("value")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private String path;

    @JsonSerialize(using = SnakeCaseMapSerializer.class)
    @JsonProperty("details")
    private Map<String,?> details;
}