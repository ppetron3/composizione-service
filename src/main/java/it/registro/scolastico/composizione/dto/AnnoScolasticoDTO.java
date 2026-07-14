package it.registro.scolastico.composizione.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

/**
 * DTO di input usato per la creazione di un AnnoScolastico
 * 
 * Contiene i confini temporali dell'anno e il flag che indica
 * se deve essere considerato l'anno corrente
*/

public record AnnoScolasticoDTO(

    @NotNull(message = "La data di inizio è obbligatoria")
    LocalDate dataInizio,

    @NotNull(message = "La data di fine è obbligatoria")
    LocalDate dataFine,

    boolean annoCorrente

) {}