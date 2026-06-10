package com.khaled_amin.book_social_network.auth.account.api.mapper;

import com.khaled_amin.book_social_network.auth.account.api.dto.AccountActivationResponse;
import com.khaled_amin.book_social_network.auth.account.api.dto.AccountLoginResponse;
import com.khaled_amin.book_social_network.auth.account.api.dto.AccountRegistrationRequest;
import com.khaled_amin.book_social_network.auth.account.api.dto.AccountRegistrationResponse;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.security.jwt.JwtMapper;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T01:11:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AccountAuthMapperImpl implements AccountAuthMapper {

    @Autowired
    private JwtMapper jwtMapper;

    @Override
    public AccountRegistrationResponse toRegistrationResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountRegistrationResponse.AccountRegistrationResponseBuilder accountRegistrationResponse = AccountRegistrationResponse.builder();

        if ( account.getEmailAddress() != null ) {
            accountRegistrationResponse.email( account.getEmailAddress() );
        }

        accountRegistrationResponse.status( account.getAccountStatus().name() );

        return accountRegistrationResponse.build();
    }

    @Override
    public AccountActivationResponse toActivationResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountActivationResponse.AccountActivationResponseBuilder accountActivationResponse = AccountActivationResponse.builder();

        if ( account.getEmailAddress() != null ) {
            accountActivationResponse.email( account.getEmailAddress() );
        }

        accountActivationResponse.status( account.getAccountStatus().name() );

        return accountActivationResponse.build();
    }

    @Override
    public AccountLoginResponse toLoginResponse(String jwtToken, AccountPrincipal principal) {
        if ( jwtToken == null && principal == null ) {
            return null;
        }

        AccountLoginResponse.AccountLoginResponseBuilder<?, ?> accountLoginResponse = AccountLoginResponse.builder();

        if ( jwtToken != null ) {
            accountLoginResponse.token( jwtMapper.toResponse( jwtToken ) );
        }
        if ( principal != null ) {
            accountLoginResponse.account( toAccountInfo( principal ) );
        }

        return accountLoginResponse.build();
    }

    @Override
    public AccountLoginResponse.AccountInfo toAccountInfo(AccountPrincipal principal) {
        if ( principal == null ) {
            return null;
        }

        AccountLoginResponse.AccountInfo.AccountInfoBuilder<?, ?> accountInfo = AccountLoginResponse.AccountInfo.builder();

        if ( principal.getSubject() != null ) {
            accountInfo.username( principal.getSubject() );
        }

        accountInfo.actorType( principal.getActorType().name() );
        accountInfo.actorCode( principal.getActorCode().getValue() );
        accountInfo.roles( mapRoles(principal.getRoles()) );

        return accountInfo.build();
    }

    @Override
    public AccountCreateRequest toCreateRequest(AccountRegistrationRequest request, List<String> roleNames) {
        if ( request == null && roleNames == null ) {
            return null;
        }

        AccountCreateRequest.AccountCreateRequestBuilder accountCreateRequest = AccountCreateRequest.builder();

        if ( request != null ) {
            if ( request.getUsername() != null ) {
                accountCreateRequest.username( request.getUsername() );
            }
            if ( request.getPassword() != null ) {
                accountCreateRequest.password( request.getPassword() );
            }
            if ( request.getEmailAddress() != null ) {
                accountCreateRequest.emailAddress( request.getEmailAddress() );
            }
            if ( request.getFirstName() != null ) {
                accountCreateRequest.firstName( request.getFirstName() );
            }
            if ( request.getLastName() != null ) {
                accountCreateRequest.lastName( request.getLastName() );
            }
        }
        List<String> list = roleNames;
        if ( list != null ) {
            accountCreateRequest.roleNames( new ArrayList<String>( list ) );
        }

        return accountCreateRequest.build();
    }
}
