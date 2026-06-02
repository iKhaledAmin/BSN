package com.khaled_amin.book_social_network.identity.user.role.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.application.provider.CapabilityProvider;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleCapabilityProvider implements CapabilityProvider {

    @Override
    public Set<RoleCapability> getCapabilities() {
        return Set.of(RoleCapability.values());
    }
}