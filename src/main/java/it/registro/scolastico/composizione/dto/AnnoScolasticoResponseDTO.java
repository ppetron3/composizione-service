package it.registro.scolastico.composizione.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import it.registro.scolastico.composizione.model.AnnoScolastico;

/**
 * DTO di output per restituire al client una rappresentazione
 * leggibile di AnnoScolastico.
 * 
 * Include sia i dati persistenti sia un'etichetta derivata dalle date.
*/
public record AnnoScolasticoResponseDTO(
    UUID id,
    String etichetta,
    LocalDate dataInizio,
    LocalDate dataFine,
    boolean annoCorrente,
    boolean active,
    LocalDateTime creatoIl
) {

    /**
     * Converte un'entity AnnoScolastico nel corrispondente DTO di risposta.
     * 
     * Costruisce anche un'etichetta testuale del tipo "2025/2026"
     * a partire dai due estremi temporali dell'anno.
     * 
     * @param anno entity persistente da convertire
     * @return DTO di output pronto per essere restituito al client.
     */
    public static AnnoScolasticoResponseDTO fromEntity(AnnoScolastico anno) {
        String etichetta = anno.getDataInizio().getYear() + "/" + anno.getDataFine().getYear();

        return new AnnoScolasticoResponseDTO(
            anno.getId(),
            etichetta,
            anno.getDataInizio(),
            anno.getDataFine(),
            anno.isAnnoCorrente(),
            anno.isActive(),
            anno.getCreatoIl()
        );
    }
}