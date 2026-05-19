package com.khaled_amin.book_social_network.identity.capability.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityResponse;
import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityUpdateRequest;
import com.khaled_amin.book_social_network.identity.capability.domain.command.CapabilityUpdateCommand;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface CapabilityMapper extends BaseMapper<CapabilityResponse,Capability> {


    default CapabilityUpdateCommand toCommand(CapabilityUpdateRequest request){
        return CapabilityUpdateCommand.of(
                request.getName(),
                request.getDescription()
        );
    }
}
