package it.registro.scolastico.composizione.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.AnnoScolastico;

public interface AnnoScolasticoRepository extends JpaRepository<AnnoScolastico, UUID> {
    // Questo metodo serve se vuoi cercare un anno specifico che sia attivo
    Optional<AnnoScolastico> findByIdAndActive(UUID id, boolean active);
    
    // Questo serve per trovare l'anno corrente (che è unico)
    Optional<AnnoScolastico> findByAnnoCorrenteTrue();
    
    // Cerca un anno scolastico dove "annoCorrente = true" E "active = true"
    //Verifica che non ci siano due anni correnti attivi
    Optional<AnnoScolastico> findByAnnoCorrenteTrueAndActiveTrue(); 

    List<AnnoScolastico> findByActiveTrue();
}

