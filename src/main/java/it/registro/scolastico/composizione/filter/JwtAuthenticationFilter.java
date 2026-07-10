package it.registro.scolastico.composizione.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        
        // Se l'header non esiste o non comincia con "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Interrompi la richiesta e rispondi con "401 Unauthorized"
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token assente o non valido");
            // Fermiamo l'autenticazione
            return; // Senza questo return faremmo passare anceh richieste che volevamo bloccare
        }

        //La richiesta ha superato i controlli. Lasciamola passare
        filterChain.doFilter(request, response);
    }
}