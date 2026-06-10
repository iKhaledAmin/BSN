package com.khaled_amin.book_social_network.identity.user.role.api.mapper;

import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCapabilityResponse;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleCapability;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T01:11:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class RoleCapabilityMapperImpl implements RoleCapabilityMapper {

    @Autowired
    private IdentityMapper identityMapper;

    @Override
    public RoleCapabilityResponse toResponse(RoleCapability entity) {
        if ( entity == null ) {
            return null;
        }

        RoleCapabilityResponse.RoleCapabilityResponseBuilder roleCapabilityResponse = RoleCapabilityResponse.builder();

        String name = entityCapabilityName( entity );
        if ( name != null ) {
            roleCapabilityResponse.name( name );
        }
        String code = entityCapabilityCode( entity );
        if ( code != null ) {
            roleCapabilityResponse.code( code );
        }
        if ( entity.getCreatedAt() != null ) {
            roleCapabilityResponse.addedAt( entity.getCreatedAt() );
        }
        if ( entity.getCreatedBy() != null ) {
            roleCapabilityResponse.addedBy( identityMapper.toResponse( entity.getCreatedBy() ) );
        }

        return roleCapabilityResponse.build();
    }

    private String entityCapabilityName(RoleCapability roleCapability) {
        if ( roleCapability == null ) {
            return null;
        }
        Capability capability = roleCapability.getCapability();
        if ( capability == null ) {
            return null;
        }
        String name = capability.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityCapabilityCode(RoleCapability roleCapability) {
        if ( roleCapability == null ) {
            return null;
        }
        Capability capability = roleCapability.getCapability();
        if ( capability == null ) {
            return null;
        }
        String code = capability.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}
