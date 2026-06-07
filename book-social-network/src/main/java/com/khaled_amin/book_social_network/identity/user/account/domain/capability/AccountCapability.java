package com.khaled_amin.book_social_network.identity.user.account.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityAction;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityDescription;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityName;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountCapability implements CapabilityDefinition {

    // -------------------------------- Self Operations -------------------------------- //

    ACCOUNT_READ_SELF(
            "ACCOUNT_READ_SELF",
            "account",
            "read_self",
            "Read Own Account",
            "Allows authenticated users to view their own account clientDetails",
            false
    ),

    ACCOUNT_UPDATE_SELF(
            "ACCOUNT_UPDATE_SELF",
            "account",
            "update_self",
            "Update Own Account",
            "Allows authenticated users to update their own account information",
            false
    ),

    // -------------------------------- Administrative Operations -------------------------------- //

    ACCOUNT_READ(
            "ACCOUNT_READ",
            "account",
            "read",
            "Read Accounts",
            "Allows viewing account clientDetails for any account",
            false
    ),

    ACCOUNT_CREATE(
            "ACCOUNT_CREATE",
            "account",
            "create",
            "Create Accounts",
            "Allows admins to create new accounts",
            false
    ),

    ACCOUNT_UPDATE(
            "ACCOUNT_UPDATE",
            "account",
            "update",
            "Update Accounts",
            "Allows updating account information for any account",
            false
    ),


    ACCOUNT_ASSIGN_ROLE(
            "ACCOUNT_ASSIGN_ROLE",
            "account",
            "assign_role",
            "Assign Account Roles",
            "Allows assigning roles to accounts",
            false
    ),

    ACCOUNT_REMOVE_ROLE(
            "ACCOUNT_REMOVE_ROLE",
            "account",
            "remove_role",
            "Remove Account Roles",
            "Allows removing assigned roles from accounts",
            false
    ),

    ACCOUNT_REPLACE_ROLES(
            "ACCOUNT_REPLACE_ROLES",
            "account",
            "replace_roles",
            "Replace Account Roles",
            "Allows replacing all assigned account roles",
            false
    ),

    // -------------------------------- Activation -------------------------------- //

    ACCOUNT_ACTIVATE(
            "ACCOUNT_ACTIVATE",
            "account",
            "activate",
            "Activate Accounts",
            "Allows activating disabled or pending accounts",
            false
    ),


    ACCOUNT_RESET_PASSWORD(
            "ACCOUNT_RESET_PASSWORD",
            "account",
            "reset_password",
            "Reset Account Passwords",
            "Allows resetting passwords for accounts",
            false
    );

    private final CapabilityCode code;
    private final CapabilityResource resource;
    private final CapabilityAction action;
    private final CapabilityName name;
    private final CapabilityDescription description;
    private final boolean systemManaged;

    AccountCapability(
            String code, String resource,
            String action, String name,
            String description, boolean systemManaged
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
        return SystemDomain.ACCOUNT;
    }
}