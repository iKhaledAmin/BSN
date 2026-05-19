package com.khaled_amin.book_social_network.identity.user.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleUpdateRequest {

    @JsonProperty("display_name")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Display value must contain only letters")
    @Size(max = 50, message = "Role display name must not exceed 50 characters")
    private String displayName;

    @Size(max = 255, message = "Role description must not exceed 255 characters")
    @JsonProperty("value")
    private String description;

    @JsonProperty("default_role")
    private Boolean defaultRole;

    @JsonProperty("protected_role")
    private Boolean protectedRole;

}