package com.company.securityroleconfiguration.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Boolean enabled;
    private LocalDateTime createdAt;


    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Authorities> authorities;
}
