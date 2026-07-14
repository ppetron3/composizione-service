package it.registro.scolastico.composizione.dto;

import java.util.UUID;

import it.registro.scolastico.composizione.model.Sezione;

/**
 * DTO di output per restituire una Sezione al client.
 * 
 * Espone i campi essenziali della risorsa in forma serializzabile.
 */

public record SezioneResponseDTO(

    UUID id,
    String nome, 
    String descrizione,
    boolean active

) {

    /**
     * Converte un'entity Sezione nel corrispondente DTO di risposta.
     * 
     * @param sezione entity persistente da convertire.
     * @return DTO di output pronto per la serializzazione JSON.
     */
    public static SezioneResponseDTO fromEntity(Sezione sezione){

        return new SezioneResponseDTO(
            sezione.getId(),
            sezione.getNome(),
            sezione.getDescrizione(),
            sezione.isActive()
        );
    }

}
