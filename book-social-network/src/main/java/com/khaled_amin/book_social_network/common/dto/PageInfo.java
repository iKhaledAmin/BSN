package com.khaled_amin.book_social_network.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PageInfo {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

}