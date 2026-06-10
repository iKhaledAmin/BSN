package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.identity.user.account.api.dto.ProfileResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Profile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:40:06+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileResponse toResponse(Profile entity) {
        if ( entity == null ) {
            return null;
        }

        ProfileResponse.ProfileResponseBuilder<?, ?> profileResponse = ProfileResponse.builder();

        if ( entity.getFirstName() != null ) {
            profileResponse.firstName( entity.getFirstName() );
        }
        if ( entity.getLastName() != null ) {
            profileResponse.lastName( entity.getLastName() );
        }
        if ( entity.getBirthDate() != null ) {
            profileResponse.birthDate( entity.getBirthDate() );
        }
        if ( entity.getPhoneNumber() != null ) {
            profileResponse.phoneNumber( entity.getPhoneNumber() );
        }
        if ( entity.getProfession() != null ) {
            profileResponse.profession( entity.getProfession() );
        }
        if ( entity.getGender() != null ) {
            profileResponse.gender( entity.getGender() );
        }
        if ( entity.getProfileStatus() != null ) {
            profileResponse.profileStatus( entity.getProfileStatus() );
        }

        return profileResponse.build();
    }
}
