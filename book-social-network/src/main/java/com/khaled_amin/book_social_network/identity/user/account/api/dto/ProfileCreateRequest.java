package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.FirstName;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.LastName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateRequest {

    private static final int FIRST_NAME_MAX_LENGTH = FirstName.MAX_LENGTH;
    private static final String FIRST_NAME_PATTERN = FirstName.PATTERN;

    private static final int LAST_NAME_MAX_LENGTH = LastName.MAX_LENGTH;
    private static final String LAST_NAME_PATTERN = LastName.PATTERN;

    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = FIRST_NAME_PATTERN, message = "First name format is invalid")
    @Size(max = FIRST_NAME_MAX_LENGTH, message = "First name is too long")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = LAST_NAME_PATTERN, message = "Last name format is invalid")
    @Size(max = LAST_NAME_MAX_LENGTH, message = "Last name is too long")
    @JsonProperty("last_name")
    private String lastName;

}
