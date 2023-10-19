package com.company.securityroleconfiguration.dto.response;

import com.company.securityroleconfiguration.module.Authorities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Boolean enabled;

    private Set<Authorities> authorities;

    private LocalDateTime createdAt;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(authorities)
                .map(auth -> auth.stream()
                        .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                        .toList())
                .orElse(new ArrayList<>());
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.enabled;
    }
}
