package com.company.securityroleconfiguration.service.validation;

import com.company.securityroleconfiguration.dto.ErrorDto;
import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.dto.response.UserDto;
import com.company.securityroleconfiguration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public List<ErrorDto> userValid(RequestUserDto dto) {
        List<ErrorDto> errors = new ArrayList<>();
        if (this.userRepository.existsByUsernameAndEnabledIsTrue(dto.getUsername())) {
            errors.add(new ErrorDto("username", String.format("Username already exists. Rejection value :: %s", dto.getUsername())));
        }

        return errors;
    }
}
