package com.khaled_amin.book_social_network.identity.user.account.api.dto;

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


    @Size(max = 50, message = "Email address is too long")
    @Email(message = "Invalid email address")
    @JsonProperty("email_address")
    private String emailAddress;

    @Valid
    @JsonProperty("profile_data")
    private ProfileUpdateRequest profileUpdateRequest;
}
