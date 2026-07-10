package it.registro.scolastico.composizione.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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