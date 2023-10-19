package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.dto.request.RequestCardDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.module.Card;
import com.company.securityroleconfiguration.repository.CardRepository;
import com.company.securityroleconfiguration.service.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;


    public ResponseDto<CardDto> create(RequestCardDto dto) {
        try {
            Card card = this.cardMapper.toEntity(dto);
            card.setCreatedAt(LocalDateTime.now());
            return ResponseDto.<CardDto>builder()
                    .success(true)
                    .message("OK")
                    .data(
                            this.cardMapper.toDto(
                                    this.cardRepository.save(card)
                            )
                    )
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CardDto>builder()
                    .code(-3)
                    .message(String.format("Card while saving error! message: %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<CardDto> getCard(Integer cardId) {
        return this.cardRepository.findByCardId(cardId)
                .map(card -> ResponseDto.<CardDto>builder()
                        .success(true)
                        .message("OK")
                        .data(this.cardMapper.toDto(card))
                        .build())
                .orElse(ResponseDto.<CardDto>builder()
                        .code(-1)
                        .message(String.format("Card with %d id is not found!", cardId))
                        .build());
    }
}
