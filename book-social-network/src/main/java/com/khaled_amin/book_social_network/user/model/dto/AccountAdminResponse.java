package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class AccountAdminResponse extends AccountBaseResponse {

    @JsonProperty("account_id")
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("last_login")
    private LocalDateTime lastLoginDate;

    @JsonProperty("account_roles_detailed")
    private List<AccountRoleResponse> detailedRoles;

}
