package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

public record RawPassword(String value) {

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 100;


    /**
     * Requirements password format.
     * <p>
     * Examples:
     * <li> at least one lowercase letter
     * <li> at least one uppercase letter
     * <li> at least one digit
     * <li> at least one special character
     */
    public static final String PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$";

    public RawPassword {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw AccountValidationException.invalidPassword()
                    .withClientDetails("reason", "Password must not be null or empty");
        }

        if (value.length() < MIN_LENGTH) {
            throw AccountValidationException.invalidPassword()
                    .withClientDetails("reason", "Password must be at least " + MIN_LENGTH + " characters");
        }

        if (value.length() > MAX_LENGTH) {
            throw AccountValidationException.invalidPassword()
                    .withClientDetails("reason", "Password exceeds maximum length of " + MAX_LENGTH);
        }

        if (!value.matches(PATTERN)) {
            throw AccountValidationException.invalidPassword()
                    .withClientDetails("reason",
                            "Password must contain uppercase, lowercase, digit and special character");
        }
    }

    public static RawPassword of(String value) {
        return new RawPassword(value);
    }

    @Override
    public String toString() {
        return value;
    }
}