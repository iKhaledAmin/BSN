package com.khaled_amin.book_social_network.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RegistrationRequest {

    @NotEmpty(message = "First value is mandatory")
    @NotBlank(message = "First value is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First value must contain only letters")
    @Size(max = 50, message = "First value is too long")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last value is mandatory")
    @NotBlank(message = "Last value is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last value must contain only letters")
    @Size(max = 50, message = "Last value is too long")
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
