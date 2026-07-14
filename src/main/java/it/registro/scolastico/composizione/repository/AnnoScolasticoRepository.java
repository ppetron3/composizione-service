package it.registro.scolastico.composizione.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.AnnoScolastico;

/**
 * AnnoScolasticoRepository
 * 
 * Repository JPA per l'entità AnnoScolastico
 * 
 * Espone query derivate per la ricerca dell'anno corrente,
 * degli anni attivi e di anni filtrati per stato logico
 */

public interface AnnoScolasticoRepository extends JpaRepository<AnnoScolastico, UUID> {
    
    /**
     * Cerca un anno scolastico per UUID e stato attivo/inattivo
     * 
     * @param id identificativo dell'anno scolastico
     * @param active staot logico desiderato
     * @return contenente l'anno se trovato con quello stato
     */
    Optional<AnnoScolastico> findByIdAndActive(UUID id, boolean active);
    
    /**
     * Cerca l'anno scolastico marcato come corrente 
     * @return Optional contentente l'anno corrente, se presente
     */
    Optional<AnnoScolastico> findByAnnoCorrenteTrue();
    
    /**
     * Cerca l'anno scolastico che sia contemporaneamente corrente e attivo.
     * 
     * @return Optional contenente l'anno corrente attivo, se presente 
     */
    Optional<AnnoScolastico> findByAnnoCorrenteTrueAndActiveTrue(); 

    /**
     * Restituise tutti gli anni scolastici attivi nel sistema
     * 
     * @return lista degli anni scolastici con active = true
     */
    List<AnnoScolastico> findByActiveTrue();
}

