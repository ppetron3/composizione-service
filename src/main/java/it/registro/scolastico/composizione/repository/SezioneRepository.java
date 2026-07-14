package it.registro.scolastico.composizione.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.Sezione;

/**
 * SezioneRepository
 * 
 * Repository JPA per l'entità Sezione
 * 
 * Espone query derivate orientate al soft delete,
 * lavorando principalmente sulle sezioni attive.
 */

public interface SezioneRepository extends JpaRepository<Sezione, UUID>{

    /**
     * Restituisce tutte le sezioni attive del sistema
     * 
     * @return lista delle sezioni con active = true
     */
    List<Sezione> findByActiveTrue();

    /**
     * Cerca una sezione attiva per identificativo UUID
     * 
     * @param id identificativo della sezione
     * @return Optional contenente la sezione, se esiste ed è attiva
     */
    Optional<Sezione> findByIdAndActiveTrue(UUID id);

}
