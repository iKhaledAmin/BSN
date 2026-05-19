package com.khaled_amin.book_social_network.identity.capability.domain.capabilities;

import com.khaled_amin.book_social_network.identity.capability.domain.registry.CapabilityProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CapabilityManagementProvider implements CapabilityProvider {

    @Override
    public List<CapabilityManagementCapability> getCapabilities() {
        return List.of(CapabilityManagementCapability.values());
    }
}
