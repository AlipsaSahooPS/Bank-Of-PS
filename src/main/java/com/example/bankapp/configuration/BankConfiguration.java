package com.example.bankapp.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank API",
                description = "APIs to used for Bank App",
                version = "v1"),servers = {
        @Server(
                description = "Dev",
                url = "http://localhost:8000"
        ),
        @Server(
                description = "Test",
                url = "http://localhost:8000"
        )
}
)
@SecurityScheme(
        name = "bankControllerSecurity",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT token for apis"
)
public class BankConfiguration {
}
