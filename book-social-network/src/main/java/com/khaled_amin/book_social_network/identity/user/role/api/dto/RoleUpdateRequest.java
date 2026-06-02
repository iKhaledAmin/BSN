package com.khaled_amin.book_social_network.identity.user.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
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
public class RoleUpdateRequest {

    private static final int DISPLAY_NAME_MAX_LENGTH = RoleDisplayName.MAX_LENGTH;
    private static final String DISPLAY_NAME_PATTERN = RoleDisplayName.PATTERN;
    private static final String DISPLAY_NAME_PATTERN_MESSAGE = RoleDisplayName.PATTERN_MESSAGE;

    private static final int DESCRIPTION_MAX_LENGTH = RoleDescription.MAX_LENGTH;


    @JsonProperty("display_name")
    @Pattern(regexp = DISPLAY_NAME_PATTERN, message = DISPLAY_NAME_PATTERN_MESSAGE)
    @Size(max = DISPLAY_NAME_MAX_LENGTH, message = "Display name length is invalid")
    private String displayName;

    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Role description must not exceed 255 characters")
    @JsonProperty("description")
    private String description;

    @JsonProperty("is_default")
    private Boolean defaultRole;

    @JsonProperty("is_protected")
    private Boolean protectedRole;

}