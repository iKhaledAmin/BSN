package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.core.dto.IdentityResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class AccountRoleResponse {

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("assigned_at")
    private LocalDateTime assignedAt;

    @JsonProperty("assigned_by")
    private IdentityResponse assignedBy;

}
