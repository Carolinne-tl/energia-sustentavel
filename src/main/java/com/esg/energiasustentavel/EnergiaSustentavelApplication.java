package com.esg.energiasustentavel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API de Energia Sustentável",
        version = "1.0",
        description = "Sistema de Monitoramento de Eficiência Energética e Sustentabilidade",
        contact = @Contact(
            name = "Equipe ESG",
            email = "contato@energiasustentavel.com"
        )
    )
)
@SecurityScheme(
    name = "bearer-key",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class EnergiaSustentavelApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergiaSustentavelApplication.class, args);
    }
}
