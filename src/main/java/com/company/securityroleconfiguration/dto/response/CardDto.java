package com.company.securityroleconfiguration.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Integer cardId;
    private String cardName;
    private String fullName;
    private String cardCode;
    private String cardPassword;
    private LocalDateTime createdAt;
}
