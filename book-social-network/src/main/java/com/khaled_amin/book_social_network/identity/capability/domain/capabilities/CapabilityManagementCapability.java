package com.khaled_amin.book_social_network.identity.capability.domain.capabilities;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.*;
import lombok.Getter;


@Getter
public enum CapabilityManagementCapability implements CapabilityDefinition {

    CAPABILITY_READ(
            "CAPABILITY_READ",
            "capability",
            "read",
            "Read Capabilities",
            "Allows viewing capability details",
            false
    ),


    ;

    private final CapabilityCode code;
    private final CapabilityResource resource;
    private final CapabilityAction action;
    private final CapabilityName name;
    private final CapabilityDescription description;
    private final boolean systemManaged;

    CapabilityManagementCapability(String code, String resource,
                                   String action, String name,
                                   String description, boolean systemManaged) {
        this.code = CapabilityCode.of(code);
        this.resource = CapabilityResource.of(resource);
        this.action = CapabilityAction.of(action);
        this.name = CapabilityName.of(name);
        this.description = CapabilityDescription.of(description);
        this.systemManaged = systemManaged;
    }


    @Override
    public SystemDomain getModule() {
        return SystemDomain.CAPABILITY;
    }
}