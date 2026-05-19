package com.khaled_amin.book_social_network.identity.capability.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapabilityResponse {
    private String code;
    private String resource;
    private String action;
    private String name;
    private String description;
    private String module;
}
