package it.registro.scolastico.composizione.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestore globale delle eccezioni del composizione-service.
 *
 * Traduce eccezioni applicative e tecniche in risposte HTTP uniformi,
 * leggibili e coerenti con il significato dell'errore.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Traduce una RichiestaNonValidaException in una risposta HTTP 400.
     *
     * @param ex eccezione applicativa di richiesta non valida
     * @return body JSON di errore con status 400
    */
    @ExceptionHandler(RichiestaNonValidaException.class)
    public ResponseEntity<Map<String, Object>> gestisciRichiestaNonValida(
            RichiestaNonValidaException ex) {

        Map<String, Object> body = Map.of(
            "timestamp", LocalDateTime.now(),
            "status", 400,
            "error", "Bad Request",
            "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }
    /**
     * Traduce una ConflittoException in una risposta HTTP 409.
     *
     * @param ex eccezione applicativa di conflitto
     * @return body JSON di errore con status 409
    */
    @ExceptionHandler(ConflittoException.class)
    public ResponseEntity<Map<String, Object>> gestisciConflitto(
            ConflittoException ex){
                
                Map<String, Object> body = Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", 409,
                    "error", "Conflict",
                    "message", ex.getMessage()
                );

                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(body);
            }

    /**
     * Traduce una RisorsaNonTrovataException in una risposta HTTP 404.
     *
     * @param ex eccezione applicativa lanciata dal dominio
     * @return body JSON di errore con status 404
    */        
    @ExceptionHandler(RisorsaNonTrovataException.class)
    public ResponseEntity<Map<String, Object>> gestisciRisorsaNonTrovata(
        RisorsaNonTrovataException ex){

            Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
            );

            return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(body); 
        }

        /**
         * Traduce errori di lettura/deserializzazione del body HTTP in una risposta 400.
         *
         * Questo caso copre JSON malformati o tipi di dato non convertibili
         * nel DTO atteso dal controller.
         *
         * @param ex eccezione tecnica del layer web
         * @return body JSON di errore con status 400
        */
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, Object>> gestisciJsonNonLeggibile(
            HttpMessageNotReadableException ex){
            
            Map<String, Object> body = Map.of(
            
            "timestamp", LocalDateTime.now(),
            "status", 400,
            "error", "Bad Request",
            "message", "JSON non valido o tipo di dato errato"
        );
        
        return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(body); 

    }


}