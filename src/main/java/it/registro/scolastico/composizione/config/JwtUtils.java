package it.registro.scolastico.composizione.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Usiamo una secret key fittizia 
    private static final String SECRET = "una_chiave_molto_lunga_e_sicura_di_almeno_32_caratteri_per_hs256_algoritmo";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 1. ESTRAZIONE GENERICO (per prendere qualsiasi claim)
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    // 2. ESTRAZIONE USERNAME (Campo "sub" nel JSON)
    public String estraiUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. ESTRAZIONE RUOLI (Claim personalizzato)
    @SuppressWarnings("unchecked")
    public List<String> estraiRuoli(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    // 4. VALIDAZIONE
    public boolean isTokenValido(String token, String username) {
        final String usernameEstratto = estraiUsername(token);
        return (usernameEstratto.equals(username) && !isTokenScaduto(token));
    }

    private boolean isTokenScaduto(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}