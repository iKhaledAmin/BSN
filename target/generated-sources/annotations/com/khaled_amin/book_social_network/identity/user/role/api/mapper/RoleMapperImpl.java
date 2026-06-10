package com.khaled_amin.book_social_network.identity.user.role.api.mapper;

import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCapabilityResponse;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleCapability;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:40:07+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private RoleCapabilityMapper roleCapabilityMapper;

    @Override
    public RoleResponse toResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        List<RoleCapabilityResponse> list = roleCapabilitySetToRoleCapabilityResponseList( role.getRoleCapabilities() );
        if ( list != null ) {
            roleResponse.capabilities( list );
        }
        if ( role.getName() != null ) {
            roleResponse.name( role.getName() );
        }
        if ( role.getDisplayName() != null ) {
            roleResponse.displayName( role.getDisplayName() );
        }
        if ( role.getDescription() != null ) {
            roleResponse.description( role.getDescription() );
        }
        roleResponse.defaultRole( role.isDefaultRole() );
        roleResponse.protectedRole( role.isProtectedRole() );

        return roleResponse.build();
    }

    protected List<RoleCapabilityResponse> roleCapabilitySetToRoleCapabilityResponseList(Set<RoleCapability> set) {
        if ( set == null ) {
            return null;
        }

        List<RoleCapabilityResponse> list = new ArrayList<RoleCapabilityResponse>( set.size() );
        for ( RoleCapability roleCapability : set ) {
            list.add( roleCapabilityMapper.toResponse( roleCapability ) );
        }

        return list;
    }
}
