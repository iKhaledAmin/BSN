package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AccountReplaceRolesRequest {


    @NotNull(message = "Role names must not be null")
    @NotEmpty(message = "Role names must not be empty")
    @JsonProperty("role_names")
    private List<String> roleNames;
}
