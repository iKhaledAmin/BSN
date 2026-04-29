package com.khaled_amin.book_social_network.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("value")
    private String description;

    @JsonProperty("is_default")
    private Boolean defaultRole;

    @JsonProperty("is_protected")
    private Boolean protectedRole;
}
