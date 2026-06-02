package com.khaled_amin.book_social_network.identity.user.role.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCapabilityResponse;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleCapability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = IdentityMapper.class
)
public interface RoleCapabilityMapper {

    @Mapping(target = "name", source = "capability.name")
    @Mapping(target = "code", source = "capability.code")
    @Mapping(target = "addedAt", source = "createdAt")
    @Mapping(target = "addedBy", source = "createdBy")
    RoleCapabilityResponse toResponse(RoleCapability entity);
}
