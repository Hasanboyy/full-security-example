package com.company.securityroleconfiguration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private boolean success;
    /*
     *  0 it is ok      200
     * -1 not found     404
     * -2 valid error   400
     * -3 db error      400
     * */
    private int code;
    private String message;
    private T data;
    private List<ErrorDto> errorList;
}
