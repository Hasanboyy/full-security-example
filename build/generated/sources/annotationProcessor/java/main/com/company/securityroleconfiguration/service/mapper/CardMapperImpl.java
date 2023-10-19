package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestCardDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.dto.response.CardDto.CardDtoBuilder;
import com.company.securityroleconfiguration.module.Card;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-19T20:45:23+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CardMapperImpl extends CardMapper {

    @Override
    public Card toEntity(RequestCardDto dto) {
        if ( dto == null ) {
            return null;
        }

        Card card = new Card();

        card.setCardName( dto.getCardName() );
        card.setFullName( dto.getFullName() );
        card.setCardCode( dto.getCardCode() );
        card.setCardPassword( dto.getCardPassword() );

        return card;
    }

    @Override
    public CardDto toDto(Card save) {
        if ( save == null ) {
            return null;
        }

        CardDtoBuilder cardDto = CardDto.builder();

        cardDto.cardId( save.getCardId() );
        cardDto.cardName( save.getCardName() );
        cardDto.fullName( save.getFullName() );
        cardDto.cardCode( save.getCardCode() );
        cardDto.cardPassword( save.getCardPassword() );
        cardDto.createdAt( save.getCreatedAt() );

        return cardDto.build();
    }
}
