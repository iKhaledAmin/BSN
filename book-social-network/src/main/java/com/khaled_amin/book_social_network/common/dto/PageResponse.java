package com.khaled_amin.book_social_network.common.dto;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class PageResponse<T> {

    private Meta meta;

    private List<T> data;

    private PageInfo page;

}