package com.khaled_amin.book_social_network.identity.user.role.domain.definition;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;

import java.util.Set;

public interface SystemRoleCapabilityDefinition {

    SystemRole getRole();

    Set<CapabilityCode> getCapabilityCodes();

}