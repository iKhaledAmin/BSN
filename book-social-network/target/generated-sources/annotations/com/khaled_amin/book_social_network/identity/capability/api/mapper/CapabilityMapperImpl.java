package com.khaled_amin.book_social_network.identity.capability.api.mapper;

import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityResponse;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T01:11:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class CapabilityMapperImpl implements CapabilityMapper {

    @Override
    public CapabilityResponse toResponse(Capability entity) {
        if ( entity == null ) {
            return null;
        }

        CapabilityResponse.CapabilityResponseBuilder capabilityResponse = CapabilityResponse.builder();

        if ( entity.getCode() != null ) {
            capabilityResponse.code( entity.getCode() );
        }
        if ( entity.getResource() != null ) {
            capabilityResponse.resource( entity.getResource() );
        }
        if ( entity.getAction() != null ) {
            capabilityResponse.action( entity.getAction() );
        }
        if ( entity.getName() != null ) {
            capabilityResponse.name( entity.getName() );
        }
        if ( entity.getDescription() != null ) {
            capabilityResponse.description( entity.getDescription() );
        }
        if ( entity.getModule() != null ) {
            capabilityResponse.module( entity.getModule().name() );
        }
        capabilityResponse.systemManaged( String.valueOf( entity.isSystemManaged() ) );

        return capabilityResponse.build();
    }
}
