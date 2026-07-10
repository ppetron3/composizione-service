package it.registro.scolastico.composizione.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class VersioningFilter extends OncePerRequestFilter{

    // Il filtro deve chiedersi questo
    // "La richiesta che sta arrivando è già nel formato /api/v1/... oppure è una path esterna da riscrivere? */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();

        //Se la richiesta è già nel formato interno, la lasciamo passare
        if(uri.startsWith("/api/v1")){
            filterChain.doFilter(request, response);
            return;
        }

        //Se la richiesta riguarda una path esterna al nostro microservizio
        //la inoltriamo alla path con /api/v1 davanti
        if(uri.equals("/anni-scolastici") || uri.startsWith("/anni-scolastici/")
        || uri.equals("/sezioni") || uri.startsWith("/sezioni/")
        || uri.equals("/classi") || uri.startsWith("/classi/")) {
            String nuovoPath = "/api/v1" + uri;
            request.getRequestDispatcher(nuovoPath).forward(request, response);
            return;
        }

        // Tutto il resto passa senza modifiche
        filterChain.doFilter(request, response);
    }
}
