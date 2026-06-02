package com.khaled_amin.book_social_network.identity.user.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.core.dto.IdentityResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoleCapabilityResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("added_at")
    private LocalDateTime addedAt;

    @JsonProperty("added_by")
    private IdentityResponse addedBy;
}
