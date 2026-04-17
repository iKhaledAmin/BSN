package com.khaled_amin.book_social_network.core.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class PageResponse<T> {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("page")
    private PageInfo page;

}