package it.registro.scolastico.composizione.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO di input usato per la creazione di una Sezione.
 * 
 * Contiene il nome obbligatorio della sezione e una descrizione opzionale.
 */

public record SezioneDTO(
    @NotBlank(message= "Devi inserire un nome")
    @Size(max= 10, message= "Il nome non deve superare i 10 caratteri")
    String nome,
    
    String descrizione

) {

}
