package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class AccountUpdateRequest {

    @Size(max = 50, message = "Username is too long")
    @JsonProperty("username")
    private String username;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @JsonProperty("password")
    private String password;

    @Size(max = 50, message = "Email address is too long")
    @Email(message = "Invalid email address")
    @JsonProperty("email_address")
    private String emailAddress;

    @Valid
    @JsonProperty("profile_data")
    private UpdateProfileRequest updateProfileRequest;
}
