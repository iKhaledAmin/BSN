package com.khaled_amin.book_social_network.identity.capability.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("is_system_managed")
    private String systemManaged;
}
