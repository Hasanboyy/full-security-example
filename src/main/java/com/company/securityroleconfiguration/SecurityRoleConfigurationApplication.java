package com.company.securityroleconfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                version = "v: 3.0.2",
                title = "Security Role Configuration",
                description = "Security Role Configuration"
        ),
        servers = {
                @Server(url = "http://localhost:8080") //--> localhost
        }
)
public class SecurityRoleConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityRoleConfigurationApplication.class, args);
    }

}
