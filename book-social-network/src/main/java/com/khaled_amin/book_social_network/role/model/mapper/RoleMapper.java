package com.khaled_amin.book_social_network.role.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import org.mapstruct.Mapper;


@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper extends BaseMapper<RoleRequest,RoleResponse,Role> {

}
