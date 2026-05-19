package com.khaled_amin.book_social_network.identity.capability.domain.capabilities;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.value.*;

public enum CapabilityManagementCapability implements CapabilityDefinition {

    CAPABILITY_READ(
            "CAPABILITY_READ",
            "capability",
            "read",
            "Read Capabilities",
            "Allows viewing capability details"
    ),

    CAPABILITY_UPDATE(
            "CAPABILITY_UPDATE",
            "capability",
            "update",
            "Update Capabilities",
            "Allows updating capability metadata"
    );

    private final CapabilityCode code;
    private final CapabilityResource resource;
    private final CapabilityAction action;
    private final CapabilityName name;
    private final CapabilityDescription description;

    CapabilityManagementCapability(
            String code,
            String resource,
            String action,
            String name,
            String description
    ) {
        this.code = CapabilityCode.of(code);
        this.resource = CapabilityResource.of(resource);
        this.action = CapabilityAction.of(action);
        this.name = CapabilityName.of(name);
        this.description = CapabilityDescription.of(description);
    }

    @Override
    public CapabilityCode getCode() {
        return code;
    }

    @Override
    public CapabilityResource getResource() {
        return resource;
    }

    @Override
    public CapabilityAction getAction() {
        return action;
    }

    @Override
    public CapabilityName getName() {
        return name;
    }

    @Override
    public CapabilityDescription getDescription() {
        return description;
    }

    @Override
    public CapabilityModule getModule() {
        return CapabilityModule.CAPABILITY;
    }
}