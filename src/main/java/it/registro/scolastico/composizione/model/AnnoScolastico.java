package it.registro.scolastico.composizione.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * AnnoScolastico
 * 
 * Entità JPA che rappresenta un anno scolastico nel sistema-
 * 
 * Modella il contesto temporale del dominio e contiene:
 * - data di inizio e fine
 * - indicazioni di anno corrente
 * - stato logico attvo/inattivo
 * - timestamp della creazione
 */

@Entity
@Table(name = "anni_scolastici")
public class AnnoScolastico {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio; 

    @Column(name = "data_fine", nullable = false)
    private LocalDate dataFine;

    @Column(name = "anno_corrente", nullable = false)
    private boolean annoCorrente;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "creato_il", updatable = false, nullable = false)
    private LocalDateTime creatoIl;

    // Costruttore vuoto esplicito per JPA
    public AnnoScolastico() {}

    // GETTER E SETTER STANDARD (Pienamente compatibili con Java 26)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public boolean isAnnoCorrente() {
        return annoCorrente;
    }

    public void setAnnoCorrente(boolean annoCorrente) {
        this.annoCorrente = annoCorrente;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatoIl() {
        return creatoIl;
    }

    public void setCreatoIl(LocalDateTime creatoIl) {
        this.creatoIl = creatoIl;
    }
}