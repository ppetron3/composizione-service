package it.registro.scolastico.composizione.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import it.registro.scolastico.composizione.model.AnnoScolastico;

public record AnnoScolasticoResponseDTO(
    UUID id,
    String etichetta,
    LocalDate dataInizio,
    LocalDate dataFine,
    boolean annoCorrente,
    boolean active,
    LocalDateTime creatoIl
) {

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