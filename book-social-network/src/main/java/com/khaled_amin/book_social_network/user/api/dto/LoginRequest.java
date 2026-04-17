package com.khaled_amin.book_social_network.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class LoginRequest {

    @NotEmpty(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username is too long")
    @JsonProperty("username")
    private String username;

    @NotEmpty(message = "Password can is mandatory")
    @NotBlank(message = "Password can is mandatory")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @JsonProperty("password")
    private String password;

}
