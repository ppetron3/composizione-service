package it.registro.scolastico.composizione.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.registro.scolastico.composizione.dto.SezioneDTO;
import it.registro.scolastico.composizione.exception.RichiestaNonValidaException;
import it.registro.scolastico.composizione.exception.RisorsaNonTrovataException;
import it.registro.scolastico.composizione.model.Sezione;
import it.registro.scolastico.composizione.repository.SezioneRepository;

@Service
public class SezioneService {

    private final SezioneRepository sezioneRepository; 
    
    public SezioneService(SezioneRepository sezioneRepository){
        this.sezioneRepository = sezioneRepository;
    }

    
    /**
     * Crea una nuova sezione a partire dai dati ricevuti dal client.
     * 
     * Converte il DTO di input in una entity JPA, assegna un UUID 
     * e inizializza la sezione come attiva.
     * 
     * @param dto dati in ingresso della sezione
     * @return sezione salvata nel database
     */
    public Sezione creaSezione(SezioneDTO dto){

        Sezione sezione = new Sezione();

        sezione.setId(UUID.randomUUID());
        sezione.setNome(dto.nome());
        sezione.setDescrizione(dto.descrizione());
        sezione.setActive(true);

        // Passaggio al layer "repository" che si occuperà del salvataggio nel DB
        return sezioneRepository.save(sezione);

    }

    // Metodo per salvare una sezione nel DB
    public Sezione salva(Sezione sezione){
        return this.sezioneRepository.save(sezione);
    }

    // Metodo per recuperare una sezione tramite il suo id
    public Sezione cercaPerId(UUID id){
        return this.sezioneRepository.findByIdAndActiveTrue(id)
            .orElseThrow(() -> new RisorsaNonTrovataException("Risorsa non trovata"));
    }

    /**
     * Restituisce tutte le sezioni attive del sistema.
     * 
     * @return lista delle sezioni con active= true
    */
    public List<Sezione> cercaTutti(){
        return this.sezioneRepository.findByActiveTrue();
    }

    /**
     * Esegue il soft delete di una sezione
     * 
     * La sezione non viene eliminata fisicamente, ma solo disattivata.
     * 
     * @param id identificativo UUID della sezione
     * @throws RisorsaNonTrovataException se la sezione non esiste
     * @throws RichiestaNonValidaException se la sezione è già inattiva
     */
    public void cancellaSezione(UUID id){

        Sezione sezione = sezioneRepository.findById(id)
            .orElseThrow(() -> new RisorsaNonTrovataException("Sezione non trovata"));
    
        if(!sezione.isActive()){
        throw new RichiestaNonValidaException("La sezione è già stata disattivata");
    }

    sezione.setActive(false);
    sezioneRepository.save(sezione);

    }

}
