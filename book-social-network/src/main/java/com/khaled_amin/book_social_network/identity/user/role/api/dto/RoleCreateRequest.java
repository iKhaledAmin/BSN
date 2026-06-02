package com.khaled_amin.book_social_network.identity.user.role.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequest {

    private static final int NAME_MAX_LENGTH = RoleName.MAX_LENGTH;
    private static final String NAME_PATTERN = RoleName.PATTERN;
    private static final String NAME_PATTERN_MESSAGE = RoleName.PATTERN_MESSAGE;

    private static final int DISPLAY_NAME_MAX_LENGTH = RoleDisplayName.MAX_LENGTH;
    private static final String DISPLAY_NAME_PATTERN = RoleDisplayName.PATTERN;
    private static final String DISPLAY_NAME_PATTERN_MESSAGE = RoleDisplayName.PATTERN_MESSAGE;

    private static final int DESCRIPTION_MAX_LENGTH = RoleDescription.MAX_LENGTH;

    @NotBlank(message = "Role name is mandatory")
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    @Size(max = NAME_MAX_LENGTH, message = "Role name length is invalid")
    @JsonProperty("name")
    private String name;

    @JsonProperty("display_name")
    @NotBlank(message = "Role display name is mandatory")
    @Pattern(regexp = DISPLAY_NAME_PATTERN, message = DISPLAY_NAME_PATTERN_MESSAGE)
    @Size(max = DISPLAY_NAME_MAX_LENGTH, message = "Role display name length is invalid")
    private String displayName;

    @NotBlank(message = "Role description is mandatory")
    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Role description length is invalid")
    @JsonProperty("description")
    private String description;


    @NotNull(message = "Default role is mandatory")
    @JsonProperty("is_default")
    private Boolean defaultRole;

    @NotNull(message = "Protected role is mandatory")
    @JsonProperty("is_protected")
    private Boolean protectedRole;

}