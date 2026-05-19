package com.khaled_amin.book_social_network.identity.user.role.domain.definition;

import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class SuperAdminRoleCapabilityDefinition implements SystemRoleCapabilityDefinition {
    private final CapabilityService capabilityService;

    @Override
    public SystemRole getRole() {
        return SystemRole.SUPER_ADMIN;
    }

    @Override
    public Set<CapabilityCode> getCapabilityCodes() {
        return capabilityService.getAll().
                stream()
                .map(capability -> CapabilityCode.of(capability.getCode()))
                .collect(Collectors.toSet());
    }
}

