package com.khaled_amin.book_social_network.identity.user.role.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.value.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleCapability implements CapabilityDefinition {

    ROLE_CREATE(
            "ROLE_CREATE",
            "role",
            "create",
            "Create Roles",
            "Allows creating new business roles"
    ),

    ROLE_READ(
            "ROLE_READ",
            "role",
            "read",
            "Read Roles",
            "Allows viewing role details"
    ),

    ROLE_UPDATE(
            "ROLE_UPDATE",
            "role",
            "update",
            "Update Roles",
            "Allows updating existing roles"
    ),

    ROLE_DELETE(
            "ROLE_DELETE",
            "role",
            "delete",
            "Delete Roles",
            "Allows deleting non protected roles"
    ),

    ROLE_ASSIGN_CAPABILITY(
            "ROLE_ASSIGN_CAPABILITY",
            "role",
            "assign_capability",
            "Assign Capability To Role",
            "Allows assigning capabilities to roles"
    ),

    ROLE_REMOVE_CAPABILITY(
            "ROLE_REMOVE_CAPABILITY",
            "role",
            "remove_capability",
            "Remove Capability From Role",
            "Allows removing capabilities from roles"
    )


    ;


    private final CapabilityCode code;
    private final CapabilityResource resource;
    private final CapabilityAction action;
    private final CapabilityName name;
    private final CapabilityDescription description;

    RoleCapability(
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
    public CapabilityModule getModule() {
        return CapabilityModule.ROLE;
    }
}