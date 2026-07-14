package it.registro.scolastico.composizione.exception;

/**
 * Eccezione applicativa usata quando una richiesta del client
 * non è valida rispetto alle regole minime dell'operazione richiesta.
 *
 * Viene tradotta dal GlobalExceptionHandler in una risposta HTTP 400.
 */
public class RichiestaNonValidaException extends RuntimeException {

    
    public RichiestaNonValidaException(String message){
        super(message);
    }

}
