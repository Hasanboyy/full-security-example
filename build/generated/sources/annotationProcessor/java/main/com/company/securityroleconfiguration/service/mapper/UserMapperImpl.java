package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.dto.response.UserDto;
import com.company.securityroleconfiguration.dto.response.UserDto.UserDtoBuilder;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.module.User;
import com.company.securityroleconfiguration.module.User.UserBuilder;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-19T20:45:23+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User toEntity(RequestUserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstname( dto.getFirstname() );
        user.lastname( dto.getLastname() );
        user.username( dto.getUsername() );

        user.enabled( true );
        user.password( this.passwordEncoder.encode(dto.getPassword()) );

        return user.build();
    }

    @Override
    public UserDto toDto(User saveUser) {
        if ( saveUser == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( saveUser.getId() );
        userDto.firstname( saveUser.getFirstname() );
        userDto.lastname( saveUser.getLastname() );
        userDto.username( saveUser.getUsername() );
        userDto.password( saveUser.getPassword() );
        userDto.enabled( saveUser.getEnabled() );
        Set<Authorities> set = saveUser.getAuthorities();
        if ( set != null ) {
            userDto.authorities( new HashSet<Authorities>( set ) );
        }
        userDto.createdAt( saveUser.getCreatedAt() );

        return userDto.build();
    }
}
