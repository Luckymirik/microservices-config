package com.example.userservice.mapper;

import com.example.userservice.dto.CompanyDTO;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "company", source = "company")
    UserResponse toUserResponse(User user, CompanyDTO company);

    User toUser(UserRequest request);
}
