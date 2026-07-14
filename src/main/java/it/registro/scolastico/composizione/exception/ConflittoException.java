package it.registro.scolastico.composizione.exception;

/**
 * Eccezione applicativa usata quando una richiesta è formalmente valida
 * ma entra in conflitto con lo stato attuale del sistema o con una regola di business.
 *
 * Viene tradotta dal GlobalExceptionHandler in una risposta HTTP 409.
 */

public class ConflittoException extends RuntimeException{

    public ConflittoException(String message){
        super(message);
    }

}
