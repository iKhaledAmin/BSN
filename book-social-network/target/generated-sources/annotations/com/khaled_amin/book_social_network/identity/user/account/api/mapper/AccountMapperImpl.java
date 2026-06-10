package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.role.api.mapper.RoleMapper;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T01:11:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl extends AccountMapper {

    @Autowired
    private IdentityMapper identityMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public AccountResponse toResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountResponse.AccountResponseBuilder<?, ?> accountResponse = AccountResponse.builder();

        List<String> list = roleMapper.mapList( account.getRoles() );
        if ( list != null ) {
            accountResponse.roles( list );
        }
        if ( account.getAccountCode() != null ) {
            accountResponse.accountCode( identityMapper.map( account.getAccountCode() ) );
        }
        if ( account.getUsername() != null ) {
            accountResponse.username( account.getUsername() );
        }
        if ( account.getEmailAddress() != null ) {
            accountResponse.emailAddress( account.getEmailAddress() );
        }
        if ( account.getAccountStatus() != null ) {
            accountResponse.accountStatus( account.getAccountStatus() );
        }
        if ( account.getProfile() != null ) {
            accountResponse.profile( profileMapper.toResponse( account.getProfile() ) );
        }

        return accountResponse.build();
    }
}
