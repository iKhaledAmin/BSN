package com.khaled_amin.book_social_network.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AccountReplaceRolesRequest {


    @NotNull(message = "Role IDs must not be null")
    @NotEmpty(message = "Role IDs must not be empty")
    @JsonProperty("role_ids")
    private List<Long> roleIds;
}
