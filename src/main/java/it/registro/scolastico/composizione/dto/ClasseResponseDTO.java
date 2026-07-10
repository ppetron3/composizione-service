package it.registro.scolastico.composizione.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import it.registro.scolastico.composizione.model.Classe;


public record ClasseResponseDTO(

    UUID id,
    int numeroAnno,
    UUID idSezione,
    String nomeSezione,
    UUID idAnnoScolastico,
    String etichettaAnno,
    String aula,
    LocalDateTime creatoIl

) {


    public static ClasseResponseDTO fromEntity(Classe classe){

        int annoInizio = classe.getAnnoScolastico().getDataInizio().getYear();
        int annoFine = classe.getAnnoScolastico().getDataFine().getYear();

        String etichetta = annoInizio + "/" + annoFine;

        return new ClasseResponseDTO(
            classe.getId(),
            classe.getNumeroAnno(),
            classe.getSezione().getId(),
            classe.getSezione().getNome(),
            classe.getAnnoScolastico().getId(),
            etichetta,
            classe.getAula(),
            classe.getCreatoIl());

    }
}