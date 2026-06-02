package com.khaled_amin.book_social_network.identity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdentityResponse {
    @JsonProperty("actor_code")
    private String actorCode;

    @JsonProperty("actor_type")
    private String actorType;
}
