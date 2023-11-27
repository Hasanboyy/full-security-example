package com.company.securityroleconfiguration.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestUserDto {

    private String firstname;
    private String lastname;
    @NotBlank(message = "Username cannot be null or empty!")
    private String username;
    @NotBlank(message = "Password cannot be null or empty!")
    private String password;

}
