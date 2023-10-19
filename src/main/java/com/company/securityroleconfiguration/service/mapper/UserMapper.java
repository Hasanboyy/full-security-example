package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.dto.response.UserDto;
import com.company.securityroleconfiguration.module.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "password", expression = "java(this.passwordEncoder.encode(dto.getPassword()))")
    public abstract User toEntity(RequestUserDto dto);


    public abstract UserDto toDto(User saveUser);
}
