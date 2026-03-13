package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.user.model.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class AccountResponse {

    @JsonProperty("account_id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("account_status")
    private AccountStatus accountStatus ;

    @JsonProperty("account_roles")
    private List<RoleResponse> roleResponses;

    @JsonProperty("profile_data")
    private ProfileResponse profileResponse;



}
