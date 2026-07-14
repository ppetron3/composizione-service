package it.registro.scolastico.composizione.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.registro.scolastico.composizione.dto.AnnoScolasticoDTO;
import it.registro.scolastico.composizione.exception.ConflittoException;
import it.registro.scolastico.composizione.exception.RichiestaNonValidaException;
import it.registro.scolastico.composizione.exception.RisorsaNonTrovataException;
import it.registro.scolastico.composizione.model.AnnoScolastico;
import it.registro.scolastico.composizione.repository.AnnoScolasticoRepository;



@Service
public class AnnoScolasticoService {

    private final AnnoScolasticoRepository annoScolasticoRepository;

    public AnnoScolasticoService(AnnoScolasticoRepository annoScolasticoRepository) {
        this.annoScolasticoRepository = annoScolasticoRepository;
    }

    /**
     * Crea un nuovo anno scolastico applicando le regole di business del dominio
     * 
     * Verifica che la data di fine sia successiva alla data di inizio
     * e che non esiste già un anno scolastico corrente attivo
     * quando il DTO richiede la creazione di un nuovo anno corrente.
     * 
     * @param dto dati in ingresso per la creazione di un anno scolastico
     * @return l'entità AnnoScolastico salvata nel database
     * @throws RichiestaNonValidaException se le date non sono coerenti
     * @throws ConflittoException se esiste già un anno corrente attivo
     */
        public AnnoScolastico creaAnnoScolastico(AnnoScolasticoDTO dto) {
            
            // se la data di fine non è dopo la data di inizio, blocca la creazione.
            if (!dto.dataFine().isAfter(dto.dataInizio())) {
                throw new RichiestaNonValidaException(
                    "La data di fine deve essere successiva alla data di inizio"
                );
            }
            
            if(dto.annoCorrente() && //Verifico che l'anno scolastico corrente non sia già salvato nel DB 
               annoScolasticoRepository.findByAnnoCorrenteTrueAndActiveTrue().isPresent()){

                throw new ConflittoException(
                    "Esiste già un anno scolastico corrente attivo"
                );
            }

            AnnoScolastico anno = new AnnoScolastico();

            anno.setId(UUID.randomUUID());
            anno.setDataInizio(dto.dataInizio());
            anno.setDataFine(dto.dataFine());
            anno.setAnnoCorrente(dto.annoCorrente());
            anno.setActive(true);
            anno.setCreatoIl(LocalDateTime.now());

            return annoScolasticoRepository.save(anno);
        }

    public AnnoScolastico salva(AnnoScolastico anno) {
        return annoScolasticoRepository.save(anno);
    }

    public Optional<AnnoScolastico> cercaPerId(UUID id) {
        return this.annoScolasticoRepository.findByIdAndActive(id, true);
    }

    public List<AnnoScolastico> cercaTutti() {
        return this.annoScolasticoRepository.findByActiveTrue();
    }

    /**
     * Restituisce l'anno scolastico corrente attualmente attivo
     * 
     * 
     * @return anno scolastico corrente
     * @throws RisorsaNonTrovataException se nel sistema non esiste alcun anno corrente attivo
     */
    public AnnoScolastico cercaAnnoCorrente(){
    
        return annoScolasticoRepository.findByAnnoCorrenteTrueAndActiveTrue()
            .orElseThrow(() -> new RisorsaNonTrovataException(
                "Anno scolastico corrente non trovato"));
    
    }

    /**
     * Disattiva logicamente un anno scolastico senza eliminarlo fisicamente dal database.
     * 
     * @param id identificativo UUID dell'anno scolastico
     * @throws RisorsaNonTrovataException se l'anno non esiste
     * @throws RichiestaNonValidaException se l'anno è già inattivo
     */
    public void cancellaAnnoScolastico(UUID id){

        AnnoScolastico anno = annoScolasticoRepository.findById(id)
        .orElseThrow(( ) -> new RisorsaNonTrovataException("Anno scolastico non trovato") );

        if(!anno.isActive()){
            throw new RichiestaNonValidaException("L'anno scolastico è già stato disattivato");
        }

        anno.setActive(false);
        annoScolasticoRepository.save(anno);
    }

}

    