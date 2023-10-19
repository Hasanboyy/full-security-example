package com.company.securityroleconfiguration.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestAuthDto {

    private String username;
    //todo: Role -> USER, ADMIN, SUPER_ADMIN
    private String authority;
    private Integer userId;

}
