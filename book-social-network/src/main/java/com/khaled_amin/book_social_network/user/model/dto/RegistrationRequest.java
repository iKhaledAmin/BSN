package com.khaled_amin.book_social_network.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First name must contain only letters")
    @Size(max = 50, message = "First name is too long")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last name must contain only letters")
    @Size(max = 50, message = "Last name is too long")
    @JsonProperty("last_name")
    private String lastName;

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

    @NotEmpty(message = "Email address is mandatory")
    @NotBlank(message = "Email address is mandatory")
    @Size(max = 50, message = "Email address is too long")
    @Email(message = "Invalid email address")
    @JsonProperty("email_address")
    private String emailAddress;


}
