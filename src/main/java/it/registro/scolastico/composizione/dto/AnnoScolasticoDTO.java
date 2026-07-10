package it.registro.scolastico.composizione.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record AnnoScolasticoDTO(

    @NotNull(message = "La data di inizio è obbligatoria")
    LocalDate dataInizio,

    @NotNull(message = "La data di fine è obbligatoria")
    LocalDate dataFine,

    boolean annoCorrente

) {}