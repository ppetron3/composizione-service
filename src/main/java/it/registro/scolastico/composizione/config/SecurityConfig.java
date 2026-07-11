package it.registro.scolastico.composizione.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.registro.scolastico.composizione.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabilitiamo CSRF perché siamo Stateless con JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                
                // 1. ROTTE PUBBLICHE (Swagger UI e OpenAPI docs)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // 2. ROTTE DI LETTURA (GET) -> Solo per utenti autenticati (qualsiasi ruolo)
                .requestMatchers(HttpMethod.GET, "/api/v1/anni-scolastici/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/sezioni/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/classi/**").authenticated()

                // 3. ROTTE DI SCRITTURA/MODIFICA (POST, PUT, DELETE) -> Riservate a DOCENTE o SEGRETERIA
                // Nota: Spring Security per la dicitura "hasAnyRole" aggiunge in automatico il prefisso "ROLE_"
                // che avevamo impostato nel JwtAuthenticationFilter!
                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyRole("DOCENTE", "SEGRETERIA")
                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasAnyRole("DOCENTE", "SEGRETERIA")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAnyRole("DOCENTE", "SEGRETERIA")

                // 4. TUTTO IL RESTO -> Deve essere autenticato come barriera di sicurezza finale
                .anyRequest().authenticated()
            )
            // Aggiungiamo il nostro filtro custom PRIMA del filtro standard di Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}