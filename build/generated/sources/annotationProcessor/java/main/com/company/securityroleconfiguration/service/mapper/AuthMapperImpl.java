package com.company.securityroleconfiguration.service.mapper;

import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.module.Authorities.AuthoritiesBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-19T20:25:54+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl extends AuthMapper {

    @Override
    public Authorities toEntity(RequestAuthDto dto) {
        if ( dto == null ) {
            return null;
        }

        AuthoritiesBuilder authorities = Authorities.builder();

        authorities.username( dto.getUsername() );
        authorities.authority( dto.getAuthority() );
        authorities.userId( dto.getUserId() );

        return authorities.build();
    }

    @Override
    public Authorities toDto(Authorities authorities) {
        if ( authorities == null ) {
            return null;
        }

        AuthoritiesBuilder authorities1 = Authorities.builder();

        authorities1.id( authorities.getId() );
        authorities1.username( authorities.getUsername() );
        authorities1.authority( authorities.getAuthority() );
        authorities1.userId( authorities.getUserId() );
        authorities1.createdAt( authorities.getCreatedAt() );
        authorities1.updatedAt( authorities.getUpdatedAt() );
        authorities1.deletedAt( authorities.getDeletedAt() );

        return authorities1.build();
    }

    @Override
    public Authorities updateAuth(Authorities authorities, RequestAuthDto dto) {
        if ( dto == null ) {
            return null;
        }

        if ( dto.getUsername() != null ) {
            authorities.setUsername( dto.getUsername() );
        }
        if ( dto.getAuthority() != null ) {
            authorities.setAuthority( dto.getAuthority() );
        }
        if ( dto.getUserId() != null ) {
            authorities.setUserId( dto.getUserId() );
        }

        return authorities;
    }
}
