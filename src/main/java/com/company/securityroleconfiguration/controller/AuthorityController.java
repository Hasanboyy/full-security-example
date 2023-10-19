package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.service.AuthorityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseDto<Authorities> create(@RequestBody RequestAuthDto dto) {
        return this.authorityService.create(dto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseDto<Authorities> getAuth(@PathVariable(value = "id") Integer authId) {
        return this.authorityService.getAuth(authId);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseDto<Authorities> updateAuth(
            @RequestBody RequestAuthDto dto,
            @PathVariable(value = "id") Integer authId
    ) {
        return this.authorityService.update(dto, authId);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseDto<Authorities> deleteAuth(@PathVariable(value = "id") Integer authId) {
        return this.authorityService.deleteAuth(authId);
    }


}
