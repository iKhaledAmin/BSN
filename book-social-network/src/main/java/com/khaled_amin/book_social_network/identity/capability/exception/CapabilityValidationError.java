package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CapabilityValidationError implements ValidationError {

    CODE_INVALID(
            SystemDomain.CAPABILITY,
            "CAPABILITY_CODE_INVALID",
            "Capability code is invalid"
    ),
    RESOURCE_INVALID(
            SystemDomain.CAPABILITY,
            "CAPABILITY_RESOURCE_INVALID",
            "Capability resource is invalid"
    ),
    ACTION_INVALID(
            SystemDomain.CAPABILITY,
            "CAPABILITY_ACTION_INVALID",
            "Capability action is invalid"
    ),
    NAME_INVALID(
            SystemDomain.CAPABILITY,
            "CAPABILITY_NAME_INVALID",
            "Capability name is invalid"
    ),
    DESCRIPTION_INVALID(
            SystemDomain.CAPABILITY,
            "CAPABILITY_DESCRIPTION_INVALID",
            "Capability description is invalid"
    );


    private final SystemDomain domain;
    private final String code;
    private final String message;
}