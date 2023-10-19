package com.company.securityroleconfiguration.config;

import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.module.User;
import com.company.securityroleconfiguration.repository.AuthorityRepository;
import com.company.securityroleconfiguration.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void addDefaultAdmin() {

        this.userRepository.findByUsername("Simple")
                .ifPresent(this.userRepository::delete);

        this.authorityRepository.findByUsername("Simple")
                .ifPresent(this.authorityRepository::delete);


        User saveUser = this.userRepository.save(User.builder()
                .enabled(true)
                .username("Simple")
                .createdAt(LocalDateTime.now())
                .password(encoder().encode("root"))
                .build());

        this.authorityRepository.save(Authorities.builder()
                .authority("ROLE_ADMIN")
                .userId(saveUser.getId())
                .username(saveUser.getUsername())
                .createdAt(LocalDateTime.now())
                .build());

    }


}
