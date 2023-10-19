package com.company.securityroleconfiguration.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;
    private String cardName;
    private String fullName;
    private String cardCode;
    private String cardPassword;
    private LocalDateTime createdAt;

}
