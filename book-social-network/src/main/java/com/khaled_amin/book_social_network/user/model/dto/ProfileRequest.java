package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileRequets {

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
}
