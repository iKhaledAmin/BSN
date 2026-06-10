package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountBaseResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:40:06+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AccountBaseMapperImpl implements AccountBaseMapper {

    @Autowired
    private IdentityMapper identityMapper;
    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public AccountBaseResponse toResponse(Account entity) {
        if ( entity == null ) {
            return null;
        }

        AccountBaseResponse.AccountBaseResponseBuilder<?, ?> accountBaseResponse = AccountBaseResponse.builder();

        if ( entity.getAccountCode() != null ) {
            accountBaseResponse.accountCode( identityMapper.map( entity.getAccountCode() ) );
        }
        if ( entity.getUsername() != null ) {
            accountBaseResponse.username( entity.getUsername() );
        }
        if ( entity.getEmailAddress() != null ) {
            accountBaseResponse.emailAddress( entity.getEmailAddress() );
        }
        if ( entity.getAccountStatus() != null ) {
            accountBaseResponse.accountStatus( entity.getAccountStatus() );
        }
        if ( entity.getProfile() != null ) {
            accountBaseResponse.profile( profileMapper.toResponse( entity.getProfile() ) );
        }

        return accountBaseResponse.build();
    }
}
