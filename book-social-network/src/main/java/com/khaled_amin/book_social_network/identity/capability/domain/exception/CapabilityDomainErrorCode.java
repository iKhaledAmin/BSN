package com.khaled_amin.book_social_network.identity.capability.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CapabilityDomainErrorCode implements BusinessError {

    INVALID_CAPABILITY("CAPABILITY_INVALID_CAPABILITY", HttpStatus.BAD_REQUEST, "Capability is invalid"),
    INVALID_COMMAND("CAPABILITY_INVALID_COMMAND", HttpStatus.BAD_REQUEST, "Capability command is invalid");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
