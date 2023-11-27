package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.service.AuthorityService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = Authorities.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<Authorities> create(@RequestBody RequestAuthDto dto) {
        return this.authorityService.create(dto);
    }

    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = Authorities.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<Authorities> getAuth(@PathVariable(value = "id") Integer authId) {
        return this.authorityService.getAuth(authId);
    }

    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = Authorities.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<Authorities> updateAuth(
            @RequestBody RequestAuthDto dto,
            @PathVariable(value = "id") Integer authId
    ) {
        return this.authorityService.update(dto, authId);
    }

    @DeleteMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDto.class,
                                            subTypes = Authorities.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseDto<Authorities> deleteAuth(@PathVariable(value = "id") Integer authId) {
        return this.authorityService.deleteAuth(authId);
    }


}
