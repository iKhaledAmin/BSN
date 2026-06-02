package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class AccountReplaceRolesRequest {


    @NotNull(message = "Role names is mandatory")
    @NotEmpty(message = "Role names is mandatory")
    @JsonProperty("role_names")
    private List<String> roleNames;
}
