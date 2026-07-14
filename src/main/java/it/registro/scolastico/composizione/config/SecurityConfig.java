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

/**
 * 
 * SecurityConfig
 * 
 * Configurazione generale della sicurezza del microservizio composizione-service
 * 
 * Definisce:
 * - la policy stateless basata su JWT
 * - le rotte pubbliche (Swagger/OpenAPI)
 * - le rotte accessibili con autenticazione
 * - le rotte riservate ai ruoli @DOCENTE e @SEGRETERIA
 * - il posizionamento del filtro JWT nella security chain
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Costruisce la catena di sicurezza HTTP del microservizio
     * 
     * Configura CSRF, sessioni stateless, regole di accesso per i vari endpoint
     * e inserisce il filtro JWT prima del filtro standard di Spring Security
     * 
     * @param http configuratore di sicurezza HTTP di Spring
     * @return la SecurityFilterChain finale usata dal framework
     * @throws Exception se Spring Security incontra errori di configurazione
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabilitiamo CSRF perché il servizio usa JWT ed è STATELESS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                
                // 1. ROTTE PUBBLICHE (Swagger UI e OpenAPI docs)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // 2. ROTTE DI LETTURA (GET) -> Solo per utenti autenticati (qualsiasi ruolo)
                .requestMatchers(HttpMethod.GET, "/api/v1/anni-scolastici/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/sezioni/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/classi/**").authenticated()

                // 3. ROTTE DI SCRITTURA/MODIFICA (POST, DELETE) -> Riservate a DOCENTE o SEGRETERIA
                // Le operazioni di modifica sono consentite solo a DOCENTE o SEGRETERIA 
                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyRole("DOCENTE", "SEGRETERIA")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAnyRole("DOCENTE", "SEGRETERIA")

                // 4. TUTTO IL RESTO -> Deve essere autenticato come barriera di sicurezza finale
                .anyRequest().authenticated()
            )
            // Aggiungiamo il nostro filtro JWT PRIMA del filtro standard di Spring Security
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}