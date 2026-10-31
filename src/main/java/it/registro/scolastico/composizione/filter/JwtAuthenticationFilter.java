package it.registro.scolastico.composizione.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.registro.scolastico.composizione.config.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro JWT eseguito una volta per richiesta.
 * 
 * Intercetta le richieste HTTP in ingresso, legge il token
 * dall'header Authorization, estrae username e ruoli tramite JwtUtils
 * e costruisce il contesto di autenticazione di Spring security.
 * 
 * Se il token manca o non ha il formato Bearer, la richiesta
 * prosegue come anonima e sarà SecurityConfig a decidere se bloccarla.
 * 
 * 
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

private final JwtUtils jwtUtils;

    JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    /**
     * Analizza l'header Authorization della richiesta corrente e,
     * se presente un JWT valido, registra l'utente nel SecurityConfig
     * con le authority corrispondenti ai ruoli contenuti nel token.
     * 
     * @param request richiesta HTTP in ingresso
     * @param response risposta HTTP in uscita
     * @param filterChain catena dei filtri successivi
     * @throws ServletException se il container servlet segnala un errore di filtro
     * @throws IOExcetpion se si verifica un errore I/O durante l'elaborazione
     */
@Override
protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    
    // CASO A: Il token NON c'è. Lasciamo proseguire la richiesta anonima.
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return; 
    }

    // CASO B: Il token c'è. Lo elaboriamo.
    String token = authHeader.substring(7); // Scartiamo la carta regalo
    String username = jwtUtils.estraiUsername(token); // Leggiamo lo username dal JSON

    // Se abbiamo lo username e Spring non ha ancora registrato nessuno per questa richiesta
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
    && jwtUtils.isTokenValido(token, username)) {
            
            // Estraggo la lista di ruoli (es. ["DOCENTE"]) dal JSON del token
            List<String> ruoli = jwtUtils.estraiRuoli(token);
            
            // Trasformo le stringhe in autorità di Spring
            List<SimpleGrantedAuthority> authorities = ruoli.stream()
                .map(ruolo -> new SimpleGrantedAuthority("ROLE_" + ruolo))
                .toList();
            
            // Compilo il documento di Spring Security
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(username, null, authorities);
            
            // Diciamo a Spring di associare i dettagli della richiesta corrente (IP, sessione, ecc.)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            // REGISTRAZIONE: Mettiamo l'utente nella scatola! Ora è ufficialmente autenticato
            SecurityContextHolder.getContext().setAuthentication(authentication);
        
    }
    
    // In qualunque caso, dopo aver controllato il token, la richiesta prosegue la catena
    filterChain.doFilter(request, response);
    }
}