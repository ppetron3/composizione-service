package it.registro.scolastico.composizione.dto;

import java.util.UUID;

import it.registro.scolastico.composizione.model.Sezione;

public record SezioneResponseDTO(

    UUID id,
    String nome, 
    String descrizione,
    boolean active

) {

    // Metodo per poter "ritornare" la sezione al client
    public static SezioneResponseDTO fromEntity(Sezione sezione){

        return new SezioneResponseDTO(
            sezione.getId(),
            sezione.getNome(),
            sezione.getDescrizione(),
            sezione.isActive()
        );
    }

}
