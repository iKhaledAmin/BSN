package com.khaled_amin.book_social_network.identity.user.account.domain.command;

import com.khaled_amin.book_social_network.identity.user.account.domain.model.Gender;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.*;

import java.time.LocalDate;
import java.util.Optional;

public record ProfileUpdateCommand(
        Optional<FirstName> firstName,
        Optional<LastName> lastName,
        Optional<Gender> gender,
        Optional<BirthDate> birthDate,
        Optional<PhoneNumber> phoneNumber,
        Optional<Profession> profession
) {

    public static ProfileUpdateCommand of(
            String firstName,
            String lastName,
            Gender gender,
            LocalDate birthDate,
            String phoneNumber,
            String profession
    ) {
        return new ProfileUpdateCommand(
                Optional.ofNullable(firstName).map(FirstName::of),
                Optional.ofNullable(lastName).map(LastName::of),
                Optional.ofNullable(gender),
                Optional.ofNullable(birthDate).map(BirthDate::of),
                Optional.ofNullable(phoneNumber).map(PhoneNumber::of),
                Optional.ofNullable(profession).map(Profession::of)
        );
    }
}