package com.khaled_amin.book_social_network.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@SuperBuilder
public class ApiError {


    private int status;

    private String code;

    private String message;

    private String path;

    private List<String> details;
}