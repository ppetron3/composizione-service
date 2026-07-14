package it.registro.scolastico.composizione.exception;

/**
 * RisorsaNonTrovataException
 * 
 * Eccezione applicativa usata quando una risorsa richiesta dal client
 * non esiste nel sistema oppure non è disponibile nel contesto attuale
 * 
 * Viene tradotta dal GlobalExceptionHandler in una risposta HTTP 404.
 */
public class RisorsaNonTrovataException extends RuntimeException{

    public RisorsaNonTrovataException(String message){
        super(message);
    }

}
