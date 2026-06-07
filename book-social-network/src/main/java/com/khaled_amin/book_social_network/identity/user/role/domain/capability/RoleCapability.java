package com.khaled_amin.book_social_network.identity.user.role.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
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
            "Allows creating new business roles",
            true
    ),


    ROLE_UPDATE(
            "ROLE_UPDATE",
            "role",
            "update",
            "Update Roles",
            "Allows updating existing roles",
            true
    ),

    ROLE_DELETE(
            "ROLE_DELETE",
            "role",
            "delete",
            "Delete Roles",
            "Allows deleting non protected roles",
            true
    ),

    ROLE_READ(
            "ROLE_READ",
            "role",
            "read",
            "Read Roles",
            "Allows viewing role clientDetails",
            true
    ),


    ROLE_ADD_CAPABILITY(
            "ROLE_ADD_CAPABILITY",
            "role",
            "add_capability",
            "Add Capability To Role",
            "Allows adding capabilities to roles",
            true
    ),

    ROLE_REMOVE_CAPABILITY(
            "ROLE_REMOVE_CAPABILITY",
            "role",
            "remove_capability",
            "Remove Capability From Role",
            "Allows removing capabilities from roles",
            true
    )


    ;


    private final CapabilityCode code;
    private final CapabilityResource resource;
    private final CapabilityAction action;
    private final CapabilityName name;
    private final CapabilityDescription description;
    private final boolean systemManaged;

    RoleCapability(
            String code,
            String resource,
            String action,
            String name,
            String description,
            boolean systemManaged
    ) {
        this.code = CapabilityCode.of(code);
        this.resource = CapabilityResource.of(resource);
        this.action = CapabilityAction.of(action);
        this.name = CapabilityName.of(name);
        this.description = CapabilityDescription.of(description);
        this.systemManaged = systemManaged;
    }

    @Override
    public SystemDomain getModule() {
        return SystemDomain.ROLE;
    }
}