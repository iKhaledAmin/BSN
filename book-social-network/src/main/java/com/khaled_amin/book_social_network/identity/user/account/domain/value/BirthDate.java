package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

import java.time.LocalDate;
import java.time.Period;

public record BirthDate(LocalDate value) {

    public static final int MIN_AGE = 13;

    public BirthDate {
        validate(value);
    }

    private static void validate(LocalDate value) {

        // optional field
        if (value == null) {
            return;
        }

        if (value.isAfter(LocalDate.now())) {
            throw AccountValidationException.invalidBirthDate()
                    .withClientDetails("reason", "Birth date cannot be in the future")
                    .withDebugDetails("receivedValue", value);
        }

        int age = Period.between(value, LocalDate.now()).getYears();

        if (age < MIN_AGE) {
            throw AccountValidationException.invalidBirthDate()
                    .withClientDetails("reason", "User must be at least 13 years old")
                    .withClientDetails("minimumAge", MIN_AGE)
                    .withDebugDetails("actualAge", age)
                    .withDebugDetails("receivedValue", value);
        }
    }

    public static BirthDate of(LocalDate value) {
        return new BirthDate(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}