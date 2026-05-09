package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;

public record RoleName(String value) {
    public RoleName {
        value = normalize(value);
        validate(value);
    }


    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw RoleDomainException
                    .invalidRoleName()
                    .withDetail("reason", "Role name must not be null or empty");
        }

        if (!name.matches("^[A-Z_]+$")) {
            throw  RoleDomainException
                    .invalidRoleName()
                    .withDetail("roleName", name)
                    .withDetail("reason","Only uppercase letters and underscores are allowed");
        }

        if (name.length() > 50) {
            throw RoleDomainException
                    .invalidRoleName()
                    .withDetail("reason", "Role name too long");
        }
    }
    public static RoleName of(String name) {
        return new RoleName(name);
    }
}
