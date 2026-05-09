package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;

public record RoleId(Long value) {

    public RoleId {
        if (value == null) {
            throw  RoleDomainException
                    .invalidRoleId()
                    .withDetail("reason", "Role id must not be null");
        }
    }

    public static RoleId of(Long value) {
        return new RoleId(value);
    }
}
