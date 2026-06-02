package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Gender;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class ProfileResponse {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("profession")
    private String profession;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("profile_status")
    private ProfileStatus profileStatus;
}
