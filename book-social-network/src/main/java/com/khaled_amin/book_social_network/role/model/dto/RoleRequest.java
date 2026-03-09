package com.khaled_amin.book_social_network.role.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleRequest {

    @NotEmpty(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters")
    @Size(max = 50, message = "Name is too long")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    @Size(max = 200, message = "Last name is too long")
    @JsonProperty("description")
    private String description;
}
