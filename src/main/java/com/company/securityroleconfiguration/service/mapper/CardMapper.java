package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestCardDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.module.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CardMapper {

    @Mapping(target = "cardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Card toEntity(RequestCardDto dto);

    public abstract CardDto toDto(Card save);
}
