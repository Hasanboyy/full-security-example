package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.dto.request.RequestCardDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.service.CardService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Card")
@RequiredArgsConstructor
@RequestMapping(value = "card")
public class CardController {

    public final CardService cardService;


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = CardDto.class
                                    )
                            )
                    }
            )
    })
    @PostMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<CardDto> create(@RequestBody RequestCardDto dto) {
        return this.cardService.create(dto);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = CardDto.class
                                    )
                            )
                    }
            )
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public ResponseDto<CardDto> getCard(@PathVariable(value = "id") Integer cardId) {
        return this.cardService.getCard(cardId);
    }


}
