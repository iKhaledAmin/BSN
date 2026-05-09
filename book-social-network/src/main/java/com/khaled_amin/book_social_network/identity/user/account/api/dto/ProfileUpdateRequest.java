package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Gender;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
public class ProfileUpdateRequest {

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First value must contain only letters")
    @Size(max = 50, message = "First value is too long")
    @JsonProperty("first_name")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last value must contain only letters")
    @Size(max = 50, message = "Last value is too long")
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @Size(max = 50, message = "Profession is too long")
    @JsonProperty("profession")
    private String profession;
}
