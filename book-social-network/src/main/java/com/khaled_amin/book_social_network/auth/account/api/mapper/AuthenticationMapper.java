package com.khaled_amin.book_social_network.auth.account.api.mapper;


import com.khaled_amin.book_social_network.auth.account.api.dto.ActivationResponse;
import com.khaled_amin.book_social_network.auth.account.api.dto.LoginResponse;
import com.khaled_amin.book_social_network.auth.account.api.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.auth.account.api.dto.RegistrationResponse;
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
public interface AuthenticationMapper {


    // ---------------- Registration ----------------

    @Mapping(target = "status", expression = "java(account.getAccountStatus().name())")
    @Mapping(target = "email", source = "emailAddress")
    RegistrationResponse toRegistrationResponse(Account account);

    // ---------------- Activation ----------------

    @Mapping(target = "status", expression = "java(account.getAccountStatus().name())")
    @Mapping(target = "email", source = "emailAddress")
    ActivationResponse toActivationResponse(Account account);

    // ---------------- Login ----------------

    @Mapping(target = "account", source = "principal")
    @Mapping(target = "token", source = "jwtToken")
    LoginResponse toLoginResponse(String jwtToken, AccountPrincipal principal);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "actorType", expression = "java(principal.getActorType().name())")
    @Mapping(target = "actorCode", expression = "java(principal.getActorCode().getValue())")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "roles", expression = "java(mapRoles(principal.getRoleNames()))")
    LoginResponse.AccountInfo toAccountInfo(AccountPrincipal principal);

    // ---------------- Helpers ----------------

    default List<String> mapRoles(Set<String> roles) {
        return roles == null ? List.of() : new ArrayList<>(roles);
    }
    default AccountCreateCommand toCommand(RegistrationRequest request, String encodedPassword) {
        return AccountCreateCommand.of(
                request.getUsername(),
                encodedPassword,
                request.getEmailAddress(),
                request.getFirstName(),
                request.getLastName()
        );
    }


}

