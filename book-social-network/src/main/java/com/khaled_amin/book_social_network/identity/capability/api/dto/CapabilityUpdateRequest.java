package com.khaled_amin.book_social_network.identity.capability.api.dto;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityDescription;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityName;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapabilityUpdateRequest {

    private static final int NAME_MAX_LENGTH = CapabilityName.MAX_LENGTH;
    private static final String NAME_PATTERN = CapabilityName.PATTERN;
    private static final int DESCRIPTION_MAX_LENGTH = CapabilityDescription.MAX_LENGTH;


    @Pattern(regexp = NAME_PATTERN, message = "Name must contain only letters and spaces")
    @Size(max = NAME_MAX_LENGTH, message = "Capability name is too long")
    private String name;

    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Capability description is too long")
    private String description;
}
