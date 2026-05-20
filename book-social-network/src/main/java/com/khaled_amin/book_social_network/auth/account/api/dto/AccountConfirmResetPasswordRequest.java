package com.khaled_amin.book_social_network.auth.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AccountConfirmResetPasswordRequest {

    @NotBlank(message = "Reset code must not be blank")
    private String code;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @JsonProperty("new_password")
    private String password;
}