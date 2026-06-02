package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.exception.RoleValidationException;

public record RoleDisplayName(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Human-readable role display format.
     * <p>
     * Examples:
     * <li> Super Admin
     * <li> Content Manager
     * <li> Customer Support
     */
    public static final String PATTERN = "^[A-Za-z]+(?: [A-Za-z]+)*$";
    public static final String PATTERN_MESSAGE = "Role display name must contain only letters and spaces";

    public RoleDisplayName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().replaceAll("\\s+", " ");
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw RoleValidationException.invalidDisplayName()
                    .withClientDetails("reason", "Role display name must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw RoleValidationException.invalidDisplayName()
                    .withClientDetails("reason", "Role display name exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw RoleValidationException.invalidDisplayName()
                    .withClientDetails("reason", PATTERN_MESSAGE)
                    .withClientDetails("expectedFormat", "letters_and_spaces_only")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static RoleDisplayName of(String value) {
        return new RoleDisplayName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}