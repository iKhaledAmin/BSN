package com.khaled_amin.book_social_network.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class Meta {

    private LocalDateTime timestamp;

    private String requestId;

    private String version;

}