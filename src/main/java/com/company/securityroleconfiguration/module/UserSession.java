package com.company.securityroleconfiguration.module;

import com.company.securityroleconfiguration.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24 * 7)
public class UserSession {

    @Id
    private String sessionId;
    private UserDto userDto;

}
