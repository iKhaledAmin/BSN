package com.khaled_amin.book_social_network.common.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;



public interface BaseMapper<REQUEST, RESPONSE, ENTITY> {

    ENTITY toEntity(REQUEST request);

    RESPONSE toResponse(ENTITY entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(REQUEST request, @MappingTarget ENTITY entity);

    ENTITY deepClone(ENTITY source);
}