package it.registro.scolastico.composizione.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import it.registro.scolastico.composizione.model.Classe;


/**
 * DTO di output usato per restituire al client una rapppresententazione
 * leggibile di Classe
 * 
 * Oltre agli identificativi, espone campi derivati utili al client,
 * come il nome della sezione e l'etichetta testuale dell'anno scolastico.
 */

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

    /**
     * Converte un'entity Classe nel corrispondente DTO di risposta.
     * 
     * Il metodo estrae anche informazioni derivate dalle relazioni,
     * come il nome della sezione e l'etichetta dell'anno scolastico.
     * 
     * @param classe entity persistente da convertire
     * @return DTO di risposta pronto per essere serializzato in JSON
     */

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