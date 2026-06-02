package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Gender;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.FirstName;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.LastName;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.PhoneNumber;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Profession;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {

    private static final int FIRST_NAME_MAX_LENGTH = FirstName.MAX_LENGTH;
    private static final String FIRST_NAME_PATTERN = FirstName.PATTERN;

    private static final int LAST_NAME_MAX_LENGTH = LastName.MAX_LENGTH;
    private static final String LAST_NAME_PATTERN = LastName.PATTERN;

    private static final String PHONE_NUMBER_PATTERN = PhoneNumber.PATTERN;

    private static final int PROFESSION_MAX_LENGTH = Profession.MAX_LENGTH;



    @Pattern(regexp = FIRST_NAME_PATTERN, message = "First name format is invalid")
    @Size(max = FIRST_NAME_MAX_LENGTH, message = "First name is too long")
    @JsonProperty("first_name")
    private String firstName;

    @Pattern(regexp = LAST_NAME_PATTERN, message = "Last name format is invalid")
    @Size(max = LAST_NAME_MAX_LENGTH, message = "Last name is too long")
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("gender")
    private Gender gender;

    @Past
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "Phone number format is invalid")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Size(max = PROFESSION_MAX_LENGTH, message = "Profession is too long")
    @JsonProperty("profession")
    private String profession;
}
