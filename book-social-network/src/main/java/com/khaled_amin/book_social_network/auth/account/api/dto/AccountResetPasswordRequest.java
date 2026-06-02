package com.khaled_amin.book_social_network.auth.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResetPasswordRequest {
    @NotEmpty(message = "EmailAddress address is mandatory")
    @NotBlank(message = "EmailAddress address is mandatory")
    @Size(max = 50, message = "EmailAddress address is too long")
    @Email(message = "Invalid emailAddress address")
    @JsonProperty("email_address")
    private String emailAddress;
}
