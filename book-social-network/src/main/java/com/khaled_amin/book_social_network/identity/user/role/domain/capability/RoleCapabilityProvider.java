package com.khaled_amin.book_social_network.identity.user.role.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.domain.registry.CapabilityProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleCapabilityProvider implements CapabilityProvider {

    @Override
    public List<RoleCapability> getCapabilities() {
        return List.of(RoleCapability.values());
    }
}