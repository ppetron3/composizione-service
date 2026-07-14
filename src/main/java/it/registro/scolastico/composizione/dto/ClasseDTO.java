package it.registro.scolastico.composizione.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * DTP di input usato per la creazione di una nuova Classe
 * 
 * Contiene gli identificativi minimi necessari per costruire l'entity persistente e i 
 * dati di business forniti dal client.
 * 
 * La validatazione strutturale dei campi viene espressa tramite
 * annotazioni di Bean Validation
 */

public record ClasseDTO(
    @NotNull (message= "L'id è obbligatorio")
    UUID idScuola,

    @Min(value = 1, message= "Il numero dell'anno deve essere almeno 1")
    @Max(value = 5, message= "Il numero dell'anno non può essere superiore a 5")
    int numeroAnno,

    @NotNull(message= "L'id della sezione è obbligatorio")
    UUID idSezione,

    @NotNull(message= "L'id dell'anno scolastico è obbligatorio")
    UUID idAnnoScolastico,
    
    @Size(max=50, message= "L'aula non deve superare i 50 caratteri")
    String aula
) {}