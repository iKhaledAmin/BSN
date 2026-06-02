package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

public record PhoneNumber(String value) {

    public static final String PATTERN = "^\\+?[0-9]{10,15}$";

    public PhoneNumber {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isBlank() ? null : value;
    }

    private static void validate(String value) {

        // optional field
        if (value == null) {
            return;
        }

        if (!value.matches(PATTERN)) {
            throw AccountValidationException.invalidPhoneNumber()
                    .withClientDetails("reason", "Phone number format is invalid")
                    .withClientDetails("expectedFormat", "international_phone_number")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }

    @Override
    public String toString() {
        return value;
    }
}