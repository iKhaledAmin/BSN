package com.khaled_amin.book_social_network.role.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateRoleRequest {

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Role name must contain only letters")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    @JsonProperty("name")
    private String name;

    @Size(max = 200, message = "Role description must not exceed 200 characters")
    @JsonProperty("description")
    private String description;

    @JsonProperty("default_role")
    private Boolean defaultRole;

    @JsonProperty("protected_role")
    private Boolean protectedRole;

}