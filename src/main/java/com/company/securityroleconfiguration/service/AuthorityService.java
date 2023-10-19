package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.repository.AuthorityRepository;
import com.company.securityroleconfiguration.service.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthMapper authMapper;
    private final AuthorityRepository authorityRepository;


    public ResponseDto<Authorities> create(RequestAuthDto dto) {
        try {
            Authorities authorities = this.authMapper.toEntity(dto);
            authorities.setCreatedAt(LocalDateTime.now());
            return ResponseDto.<Authorities>builder()
                    .success(true)
                    .message("OK")
                    .data(
                            this.authMapper.toDto(
                                    this.authorityRepository.save(authorities)
                            )
                    )
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Authorities>builder()
                    .code(-3)
                    .message(String.format("Authority while saving error message: %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<Authorities> getAuth(Integer authId) {
        return this.authorityRepository.findByIdAndDeletedAtIsNull(authId)
                .map(auth -> ResponseDto.<Authorities>builder()
                        .success(true)
                        .message("OK")
                        .data(this.authMapper.toDto(auth))
                        .build())
                .orElse(ResponseDto.<Authorities>builder()
                        .code(-1)
                        .message(String.format("Authority with %d : id is not found!", authId))
                        .build());
    }

    public ResponseDto<Authorities> update(RequestAuthDto dto, Integer authId) {
        try {
            return this.authorityRepository.findByIdAndDeletedAtIsNull(authId)
                    .map(authorities -> {
                        authorities.setUpdatedAt(LocalDateTime.now());
                        return ResponseDto.<Authorities>builder()
                                .success(true)
                                .message("OK")
                                .data(
                                        this.authMapper.toDto(
                                                this.authorityRepository.save(
                                                        this.authMapper.updateAuth(authorities, dto)
                                                )
                                        )
                                )
                                .build();
                    }).orElse(ResponseDto.<Authorities>builder()
                            .code(-1)
                            .message(String.format("Authority with %d : id is not found!", authId))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<Authorities>builder()
                    .code(-3)
                    .message(String.format("Authority while saving error message: %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<Authorities> deleteAuth(Integer authId) {
        try {
            return this.authorityRepository.findByIdAndDeletedAtIsNull(authId)
                    .map(auth -> {
                        auth.setDeletedAt(LocalDateTime.now());
                        return ResponseDto.<Authorities>builder()
                                .success(true)
                                .message("OK")
                                .data(
                                        this.authMapper.toDto(
                                                this.authorityRepository.save(auth)
                                        )
                                )
                                .build();
                    })
                    .orElse(ResponseDto.<Authorities>builder()
                            .code(-1)
                            .message(String.format("Authority with %d : id is not found!", authId))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<Authorities>builder()
                    .code(-3)
                    .message(String.format("Authority while saving error message: %s", e.getMessage()))
                    .build();
        }
    }
}
