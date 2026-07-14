package it.registro.scolastico.composizione.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.Classe;

/**
 * ClasseRepository
 * 
 * Repository JPA per l'entità Classe
 * 
 * Espone operazioni di accesso ai dati per il recupero di classi attive, 
 * il controllo di duplicati logici e la ricerca per anno scolastico
 */

public interface ClasseRepository extends JpaRepository<Classe, UUID>{

    /**
     * Cerca una classe esistente con la stessa combinazione di scuola,
     * anno di corso, sezione e anno scolastico.
     * 
     * Questo metodo viene usato dal service per individuare duplicati logici.
     * 
     * @param idScuola identificativo della scuola
     * @param numeroAnno anno di corso (1, 2, 3, 4, 5)
     * @param idSezione identificativo della sezione
     * @param idAnnoScolastico identificativo dell'anno scolastico
     * @return Optional contente la classe duplicata, se presente
     */
    Optional<Classe> findByIdScuolaAndNumeroAnnoAndSezioneIdAndAnnoScolasticoId(
        UUID idScuola,
        int numeroAnno,
        UUID idSezione,
        UUID idAnnoScolastico
    );

    /**
     *
     * Restituisce tutte le classi attive collegate a un determinato anno scolastico
     * 
     * @param idAnnoScolastico identificativo dell'anno scolastico
     * @return lista delle classi attive associate a quell'anno  
    */
    List<Classe> findByAnnoScolasticoIdAndActiveTrue(UUID idAnnoScolastico);

    /**
     * Cerca una classe attiva per identificativo UUID.
     * 
     * @param id identificativo della classe 
     * @return Optional contenente la classe, se esiste ed è attiva
     */
    Optional<Classe> findByIdAndActiveTrue(UUID id);
}
