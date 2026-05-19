package com.khaled_amin.book_social_network.identity.user.role.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleCreateRequest {

    @NotBlank(message = "Role name is mandatory")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must contain only uppercase letters and underscores")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    @JsonProperty("name")
    private String name;

    @JsonProperty("display_name")
    @NotBlank(message = "Role display name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Role display name must contain only letters")
    @Size(max = 50, message = "Role display name must not exceed 50 characters")
    private String displayName;

    @NotBlank(message = "Role description is mandatory")
    @Size(max = 255, message = "Role description must not exceed 255 characters")
    @JsonProperty("description")
    private String description;


    @NotNull(message = "Default role is mandatory")
    @JsonProperty("default_role")
    private Boolean defaultRole;

    @NotNull(message = "Protected role is mandatory")
    @JsonProperty("protected_role")
    private Boolean protectedRole;

}