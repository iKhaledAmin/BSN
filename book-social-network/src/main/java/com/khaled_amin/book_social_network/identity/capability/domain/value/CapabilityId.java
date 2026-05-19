package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;

public record CapabilityId(Long value) {

    public CapabilityId {
        if (value == null) {
            throw  RoleDomainException
                    .invalidRoleId()
                    .withDetail("reason", "Capability id must not be null");
        }
    }

    public static CapabilityId of(Long value) {
        return new CapabilityId(value);
    }

}