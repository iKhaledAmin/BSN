package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CapabilityTechnicalError implements TechnicalError {


    DEFINITION_NULL(
            SystemDomain.CAPABILITY,
            "CAPABILITY_DEFINITION_NULL",
            "Capability definition is null"
    ),

    CODE_DUPLICATE(
            SystemDomain.CAPABILITY,
            "CAPABILITY_CODE_DUPLICATE",
            "Capability code is duplicate"
    ),

    PROVIDER_NULL(
            SystemDomain.CAPABILITY,
            "CAPABILITY_PROVIDER_NULL",
            "Capability provider is null"
    ),

    ;

    private final SystemDomain domain;
    private final String code;
    private final String message;
}