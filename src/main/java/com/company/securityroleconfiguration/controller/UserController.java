package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.dto.request.LoginDto;
import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.dto.response.CardDto;
import com.company.securityroleconfiguration.dto.response.TokenResponseDto;
import com.company.securityroleconfiguration.dto.response.UserDto;
import com.company.securityroleconfiguration.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User")
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

    public final UserService userService;

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
    @PostMapping(value = "/register")
    //@PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseDto<UserDto> register(@RequestBody RequestUserDto userDto) {
        return this.userService.register(userDto);
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
    @PostMapping(value = "/login")
    //@PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseDto<TokenResponseDto> login(@RequestBody LoginDto dto) {
        return this.userService.login(dto);
    }


}
