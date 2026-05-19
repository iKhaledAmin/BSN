package com.khaled_amin.book_social_network.identity.user.role.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleAssignCapabilityRequest {

    @NotBlank(message = "Capability code is mandatory")
    @JsonProperty("capability_code")
    private String capabilityCode;
}
