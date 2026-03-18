package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.user.model.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class AccountBaseResponse {
    @JsonProperty("username")
    private String username;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("account_status")
    private AccountStatus accountStatus ;

    @JsonProperty("profile_data")
    private ProfileResponse profile;
}
