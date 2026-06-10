package com.khaled_amin.book_social_network.identity.core.mapper;

import com.khaled_amin.book_social_network.identity.core.dto.IdentityResponse;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:40:06+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class IdentityMapperImpl implements IdentityMapper {

    @Override
    public IdentityResponse toResponse(ActorIdentity identity) {
        if ( identity == null ) {
            return null;
        }

        IdentityResponse.IdentityResponseBuilder identityResponse = IdentityResponse.builder();

        if ( identity.getActorCode() != null ) {
            identityResponse.actorCode( map( identity.getActorCode() ) );
        }
        if ( identity.getActorType() != null ) {
            identityResponse.actorType( identity.getActorType().name() );
        }

        return identityResponse.build();
    }
}
