package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.module.Authorities;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Authorities toEntity(RequestAuthDto dto);

    public abstract Authorities toDto(Authorities authorities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, resultType = AuthMapper.class)
    public abstract Authorities updateAuth(@MappingTarget Authorities authorities, RequestAuthDto dto);
}
