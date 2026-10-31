package it.registro.scolastico.composizione.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 
 * JwtUtils
 * 
 * Utility dedicata alla gestione dei JSON Web Token
 * 
 * Si occupa di:
 * - costruire la chiave di firma
 * - leggere i claim del token
 * - estrarre username e ruoli
 * - verificare la validità e la scadenza del JWT
 */

@Component
public class JwtUtils {

    // Usiamo una secret key fittizia 
    private static final String SECRET = "una_chiave_molto_lunga_e_sicura_di_almeno_32_caratteri_per_hs256_algoritmo";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Estra un claim arbitrario dal payload del JWT dopo averne modificato la firma
     * 
     * @param token JWT da analizzare 
     * @param claimsResolver funziona che seleziona il claim desiderato
     * @param <T> tipo del valore da restituire 
     * @return il valore del claim richiesto
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    /**
     * Estrae lo username dal claim standard "sub" del token
     * 
     * @param token JWT da analizzare 
     * @return username contenuto nel token
     */
    public String estraiUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Estrae la lista dei ruoli dal claim personalizzato "roles"
     * 
     * @param token JWT da analizzare
     * @return lista dei ruoli dichiarati nel payload
     */
    @SuppressWarnings("unchecked")
    public List<String> estraiRuoli(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    /**
     * Verifica che il token sia coerente con l'utente atteso e non sia scaduto
     * 
     * @param token JWT da validare
     * @param username username atteso
     * @return true se il token è valido, false altrimenti
     */
    public boolean isTokenValido(String token, String username) {
        final String usernameEstratto = estraiUsername(token);
        return usernameEstratto.equals(username) && !isTokenScaduto(token);
    }

    private boolean isTokenScaduto(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}