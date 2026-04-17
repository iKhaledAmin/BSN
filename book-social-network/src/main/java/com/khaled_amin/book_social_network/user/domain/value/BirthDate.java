package com.khaled_amin.book_social_network.user.domain.value;

import com.khaled_amin.book_social_network.user.domain.exception.AccountDomainException;

import java.time.LocalDate;
import java.time.Period;

public record BirthDate(LocalDate value) {

    public BirthDate {
        validate(value);
    }

    private static void validate(LocalDate value) {

        if (value == null) {
            throw AccountDomainException
                    .invalidBirthDate()
                    .withDetail("reason", "Birth date cannot be null");
        }

        if (value.isAfter(LocalDate.now())) {
            throw AccountDomainException
                    .invalidBirthDate()
                    .withDetail("reason", "Birth date cannot be in the future");
        }

        int age = Period.between(value, LocalDate.now()).getYears();

        if (age < 13) {
            throw AccountDomainException
                    .invalidBirthDate()
                    .withDetail("reason", "User must be at least 13 years old");
        }
    }

    public static BirthDate of(LocalDate value) {
        return new BirthDate(value);
    }
}