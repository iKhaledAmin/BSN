package com.khaled_amin.book_social_network.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khaled_amin.book_social_network.user.model.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class AccountNormalResponse extends AccountBaseResponse{

    @JsonProperty("account_roles")
    private List<String> roles;

}
