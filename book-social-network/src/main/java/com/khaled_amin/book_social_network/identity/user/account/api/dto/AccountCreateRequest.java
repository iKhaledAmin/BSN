package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

    private static final int USERNAME_MAX_LENGTH = Username.MAX_LENGTH;
    private static final String USERNAME_PATTERN = Username.PATTERN;
    private static final int EMAIL_ADDRESS_MAX_LENGTH = EmailAddress.MAX_LENGTH;

    private static final int PASSWORD_MIN_LENGTH = RawPassword.MIN_LENGTH;
    private static final int PASSWORD_MAX_LENGTH = RawPassword.MAX_LENGTH;
    private static final String PASSWORD_PATTERN = RawPassword.PATTERN;

    private static final int FIRST_NAME_MAX_LENGTH = FirstName.MAX_LENGTH;
    private static final String FIRST_NAME_PATTERN = FirstName.PATTERN;

    private static final int LAST_NAME_MAX_LENGTH = LastName.MAX_LENGTH;
    private static final String LAST_NAME_PATTERN = LastName.PATTERN;


    @NotEmpty(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = USERNAME_PATTERN, message = "Username format is invalid")
    @Size(max = USERNAME_MAX_LENGTH, message = "Username is too long")
    @JsonProperty("username")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "Password length is invalid")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Password format is invalid")
    @JsonProperty("password")
    private String password;

    @NotEmpty(message = "Email address is mandatory")
    @NotBlank(message = "Email address is mandatory")
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH, message = "Email address is too long")
    @Email(message = "Email address format is invalid")
    @JsonProperty("email_address")
    private String emailAddress;

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


    @NotNull(message = "Role names are mandatory")
    @JsonProperty("role_names")
    List<String> roleNames;
}
