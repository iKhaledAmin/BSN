package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class AccountBaseResponse {

    @JsonProperty("account_code")
    private String accountCode;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("account_status")
    private AccountStatus accountStatus ;

    @JsonProperty("profile")
    private ProfileResponse profile;
}
