package com.khaled_amin.book_social_network.auth.account.api.mapper;


import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.security.jwt.JwtMapper;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Mapper(config = GlobalMapperConfig.class,uses = JwtMapper.class)
public interface AccountAuthenticationMapper {


    // ---------------- Registration ----------------

    @Mapping(target = "status", expression = "java(account.getAccountStatus().name())")
    @Mapping(target = "email", source = "emailAddress")
    AccountRegistrationResponse toRegistrationResponse(Account account);

    // ---------------- Activation ----------------

    @Mapping(target = "status", expression = "java(account.getAccountStatus().name())")
    @Mapping(target = "email", source = "emailAddress")
    AccountActivationResponse toActivationResponse(Account account);

    // ---------------- Login ----------------

    @Mapping(target = "account", source = "principal")
    @Mapping(target = "token", source = "jwtToken")
    AccountLoginResponse toLoginResponse(String jwtToken, AccountPrincipal principal);

    @Mapping(target = "actorType", expression = "java(principal.getActorType().name())")
    @Mapping(target = "actorCode", expression = "java(principal.getActorCode().getValue())")
    @Mapping(target = "username", source = "subject")
    @Mapping(target = "roles", expression = "java(mapRoles(principal.getRoles()))")
    AccountLoginResponse.AccountInfo toAccountInfo(AccountPrincipal principal);

    // ---------------- Helpers ----------------

    default List<String> mapRoles(Set<String> roles) {
        return roles == null ? List.of() : new ArrayList<>(roles);
    }
    default AccountCreateCommand toCommand(AccountRegistrationRequest request, String encodedPassword) {
        return AccountCreateCommand.of(
                request.getUsername(),
                encodedPassword,
                request.getEmailAddress(),
                request.getFirstName(),
                request.getLastName()
        );
    }


}

