package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.exception.RoleValidationException;

import java.util.List;
import java.util.Objects;

public record RoleName(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Canonical internal role identifier format.
     * <p>
     * Examples:
     * <li> SUPER_ADMIN
     * <li> CONTENT_MANAGER
     * <li> USER
     */
    public static final String PATTERN = "^[A-Z]+(?:_[A-Z]+)*$";
    public static final String PATTERN_MESSAGE = "Role name must contain only uppercase letters and underscores";

    public RoleName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw RoleValidationException.invalidName()
                    .withClientDetails("reason", "Role name must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw RoleValidationException.invalidName()
                    .withClientDetails("reason", "Role name exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw RoleValidationException.invalidName()
                    .withClientDetails("reason", PATTERN_MESSAGE)
                    .withClientDetails("expectedFormat", "UPPERCASE_WITH_UNDERSCORES")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static RoleName of(String value) {
        return new RoleName(value);
    }

    public static List<RoleName> of(List<String> values) {
        return values
                .stream()
                .filter(Objects::nonNull)
                .map(RoleName::of)
                .toList();
    }

    @Override
    public String toString() {
        return value;
    }
}