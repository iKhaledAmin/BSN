package com.khaled_amin.book_social_network.identity.user.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoleResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_default")
    private Boolean defaultRole;

    @JsonProperty("is_protected")
    private Boolean protectedRole;

    @JsonProperty("capabilities")
    private List<RoleCapabilityResponse> capabilities;
}
