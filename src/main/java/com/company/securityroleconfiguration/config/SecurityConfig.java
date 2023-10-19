package com.company.securityroleconfiguration.config;

import com.company.securityroleconfiguration.security.AuthEntryPointJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import com.company.securityroleconfiguration.security.JwtFilter;
import com.company.securityroleconfiguration.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthEntryPointJwt authEntryPointJwt;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] accessMethod = {
                "/users/**",
                "/swagger-ui/**",
                "/swagger-resources/*",
                "*.html",
                "/api/v1/swagger.json"
        };
        return http
                .cors().configurationSource(corsConfigurationSource())
                .and().exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and().csrf().disable().authorizeHttpRequests()
                .requestMatchers(accessMethod).permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and().build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); //todo: Allow all origins (adjust as needed)
        configuration.addAllowedMethod("*"); //todo: Allow all HTTP methods
        configuration.addAllowedHeader("*"); //todo: Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
