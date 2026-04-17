package com.khaled_amin.book_social_network.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class Meta {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("version")
    private String version;

}