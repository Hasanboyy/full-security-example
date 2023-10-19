package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.dto.ErrorDto;
import com.company.securityroleconfiguration.dto.request.LoginDto;
import com.company.securityroleconfiguration.dto.ResponseDto;
import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.dto.response.TokenResponseDto;
import com.company.securityroleconfiguration.dto.response.UserDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.module.User;
import com.company.securityroleconfiguration.module.UserSession;
import com.company.securityroleconfiguration.repository.AuthorityRepository;
import com.company.securityroleconfiguration.repository.UserRepository;
import com.company.securityroleconfiguration.repository.UserSessionRepository;
import com.company.securityroleconfiguration.security.JwtUtil;
import com.company.securityroleconfiguration.service.mapper.UserMapper;
import com.company.securityroleconfiguration.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserSessionRepository userSessionRepository;

    public ResponseDto<UserDto> register(RequestUserDto dto) {
        try {

            List<ErrorDto> errorList = this.userValidation.userValid(dto);
            if (!errorList.isEmpty()) {
                return ResponseDto.<UserDto>builder()
                        .code(-2)
                        .errorList(errorList)
                        .build();
            }


            User user = this.userMapper.toEntity(dto);
            user.setCreatedAt(LocalDateTime.now());

            User saveUser = this.userRepository.save(user);

            this.authorityRepository.save(
                    Authorities.builder()
                            .userId(saveUser.getId())
                            .username(saveUser.getUsername())
                            .authority("ROLE_USER")
                            .createdAt(LocalDateTime.now())
                            .build()
            );

            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .message("OK")
                    .data(
                            this.userMapper.toDto(saveUser)
                    )
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .message(String.format("User while saving error. Message %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<TokenResponseDto> login(LoginDto dto) {
        try {
            return this.userRepository.findByUsernameAndEnabledIsTrue(dto.getUsername())
                    .map(user -> {
                        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                            return ResponseDto.<TokenResponseDto>builder()
                                    .code(-2)
                                    .message("Password is not valid!")
                                    .build();
                        }

                        String sessionId = UUID.randomUUID().toString();
                        this.userSessionRepository.save(
                                new UserSession(
                                        sessionId,
                                        this.userMapper.toDto(user)
                                )
                        );

                        String token = jwtUtil.generateToken(sessionId);

                        return ResponseDto.<TokenResponseDto>builder()
                                .success(true)
                                .message("OK")
                                .data(TokenResponseDto.builder()
                                        .accessToken(token)
                                        .timestamp(
                                                LocalDateTime.ofInstant(
                                                        Instant.ofEpochMilli(jwtUtil.getClaims("exp", token, Long.class) * 1000),
                                                        ZoneId.systemDefault()
                                                )
                                        ).build())
                                .build();
                    }).orElse(ResponseDto.<TokenResponseDto>builder()
                            .message("Message")
                            .build());
        } catch (Exception e) {
            return ResponseDto.<TokenResponseDto>builder()
                    .code(-3)
                    .message(String.format("User while saving error. Message %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsernameAndEnabledIsTrue(username)
                .map(this.userMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("User : " + username + " not found"));
    }
}
