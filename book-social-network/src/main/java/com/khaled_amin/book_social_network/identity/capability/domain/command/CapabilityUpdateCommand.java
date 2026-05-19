package com.khaled_amin.book_social_network.identity.capability.domain.command;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityDescription;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityName;

public record CapabilityUpdateCommand(CapabilityName displayName, CapabilityDescription description) {

    public static CapabilityUpdateCommand of(String displayName, String description) {
        return new CapabilityUpdateCommand(
                CapabilityName.of(displayName),
                CapabilityDescription.of(description)
        );
    }
}
