package it.registro.scolastico.composizione.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * 
 * OpenApiConfig
 * 
 * Configurazione OpenAPI 
 * 
 * Definisce i metadati principali della documentazione API
 * e lo schema di sicurezza bearer JWT usato da Swagger UI
 */

@Configuration
public class OpenApiConfig {

 /**
  * Costruisce la descrizione OpenAPI del microservizio
  * 
  * Include titolo, versione, descrizione e configurazione 
  * dello schema di autenticazione bearer JWT.
  * 
  * @return oggetto OpenAPI usato da springdoc/Swagger UI
  */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("Registro Scolastico - Composizione Service API")
                        .version("1.0")
                        .description("Documentazione delle API del microservizio Composizione (Classi, Sezioni, Anni Scolastici)"))
                // Configura il requisito di sicurezza globale per i test dell'interfaccia UI
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Inserisci il token JWT valido per autenticarti come DOCENTE o SEGRETERIA")));
    }
}