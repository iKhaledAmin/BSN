package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountRoleResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:40:07+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AccountRoleMapperImpl implements AccountRoleMapper {

    @Autowired
    private IdentityMapper identityMapper;

    @Override
    public AccountRoleResponse toResponse(AccountRole entity) {
        if ( entity == null ) {
            return null;
        }

        AccountRoleResponse.AccountRoleResponseBuilder<?, ?> accountRoleResponse = AccountRoleResponse.builder();

        String name = entityRoleName( entity );
        if ( name != null ) {
            accountRoleResponse.roleName( name );
        }
        if ( entity.getCreatedAt() != null ) {
            accountRoleResponse.assignedAt( entity.getCreatedAt() );
        }
        if ( entity.getCreatedBy() != null ) {
            accountRoleResponse.assignedBy( identityMapper.toResponse( entity.getCreatedBy() ) );
        }

        return accountRoleResponse.build();
    }

    private String entityRoleName(AccountRole accountRole) {
        if ( accountRole == null ) {
            return null;
        }
        Role role = accountRole.getRole();
        if ( role == null ) {
            return null;
        }
        String name = role.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
