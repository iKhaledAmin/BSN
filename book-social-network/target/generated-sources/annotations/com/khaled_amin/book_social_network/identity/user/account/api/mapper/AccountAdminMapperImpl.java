package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountRoleResponse;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.ProfileResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountRole;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Profile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T01:11:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AccountAdminMapperImpl implements AccountAdminMapper {

    @Autowired
    private IdentityMapper identityMapper;
    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Override
    public AccountAdminResponse toResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountAdminResponse.AccountAdminResponseBuilder<?, ?> accountAdminResponse = AccountAdminResponse.builder();

        List<AccountRoleResponse> list = accountRoleSetToAccountRoleResponseList( account.getAccountRoles() );
        if ( list != null ) {
            accountAdminResponse.roles( list );
        }
        if ( account.getAccountCode() != null ) {
            accountAdminResponse.accountCode( identityMapper.map( account.getAccountCode() ) );
        }
        if ( account.getUsername() != null ) {
            accountAdminResponse.username( account.getUsername() );
        }
        if ( account.getEmailAddress() != null ) {
            accountAdminResponse.emailAddress( account.getEmailAddress() );
        }
        if ( account.getAccountStatus() != null ) {
            accountAdminResponse.accountStatus( account.getAccountStatus() );
        }
        if ( account.getProfile() != null ) {
            accountAdminResponse.profile( profileToProfileResponse( account.getProfile() ) );
        }
        if ( account.getCreatedAt() != null ) {
            accountAdminResponse.createdAt( account.getCreatedAt() );
        }
        if ( account.getLastLogin() != null ) {
            accountAdminResponse.lastLogin( account.getLastLogin() );
        }

        return accountAdminResponse.build();
    }

    protected List<AccountRoleResponse> accountRoleSetToAccountRoleResponseList(Set<AccountRole> set) {
        if ( set == null ) {
            return null;
        }

        List<AccountRoleResponse> list = new ArrayList<AccountRoleResponse>( set.size() );
        for ( AccountRole accountRole : set ) {
            list.add( accountRoleMapper.toResponse( accountRole ) );
        }

        return list;
    }

    protected ProfileResponse profileToProfileResponse(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileResponse.ProfileResponseBuilder<?, ?> profileResponse = ProfileResponse.builder();

        if ( profile.getFirstName() != null ) {
            profileResponse.firstName( profile.getFirstName() );
        }
        if ( profile.getLastName() != null ) {
            profileResponse.lastName( profile.getLastName() );
        }
        if ( profile.getBirthDate() != null ) {
            profileResponse.birthDate( profile.getBirthDate() );
        }
        if ( profile.getPhoneNumber() != null ) {
            profileResponse.phoneNumber( profile.getPhoneNumber() );
        }
        if ( profile.getProfession() != null ) {
            profileResponse.profession( profile.getProfession() );
        }
        if ( profile.getGender() != null ) {
            profileResponse.gender( profile.getGender() );
        }
        if ( profile.getProfileStatus() != null ) {
            profileResponse.profileStatus( profile.getProfileStatus() );
        }

        return profileResponse.build();
    }
}
