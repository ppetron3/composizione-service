package it.registro.scolastico.composizione.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.Classe;


public interface ClasseRepository extends JpaRepository<Classe, UUID>{


    // Verifica se nel DB è già presente una stessa classe con tutte questi "dati"
    Optional<Classe> findByIdScuolaAndNumeroAnnoAndSezioneIdAndAnnoScolasticoId(
        UUID idScuola,
        int numeroAnno,
        UUID idSezione,
        UUID idAnnoScolastico
    );


    // Ritorna la lista delle classi di quell'anno scolastico e "attive"
    List<Classe> findByAnnoScolasticoIdAndActiveTrue(UUID idAnnoScolastico);

    
}
