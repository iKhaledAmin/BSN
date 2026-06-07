package com.khaled_amin.book_social_network.identity.user.role.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTechnicalError implements TechnicalError {

    ROLE_NULL(
            SystemDomain.ROLE,
            "ROLE_NULL",
            "Role is null"
    ),

    SYSTEM_ROLE_NULL(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_ROLE_NULL",
            "System role is null"
    ),

    CREATE_COMMAND_NULL(
            SystemDomain.ROLE,
            "ROLE_CREATE_COMMAND_NULL",
            "Role create command is null"
    ),

    UPDATE_COMMAND_NULL(
            SystemDomain.ROLE,
            "ROLE_UPDATE_COMMAND_NULL",
            "Role update command is null"
    ),

    CAPABILITY_NULL(
            SystemDomain.ROLE,
            "ROLE_CAPABILITY_NULL",
            "Capability to be assigned is null"
    ),

    SYSTEM_ROLE_CONFIGURATION_INVALID(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_ROLE_CONFIGURATION_INVALID",
            "System role configuration is invalid"
    ),


;
    private final SystemDomain domain;
    private final String code;
    private final String message;
}
