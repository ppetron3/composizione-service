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

    // Metodo per salvare una sezione nel DB
    public Sezione salva(Sezione sezione){
        return this.sezioneRepository.save(sezione);
    }

    // Metodo per recuperare una sezione tramite il suo id
    public List<Sezione> cercaPerId(UUID id){
        return this.sezioneRepository.findByIdAndActiveTrue(id);
    }

    // Metodo per recuperare TUTTE le sezioni
    public List<Sezione> cercaTutti(){
        return this.sezioneRepository.findByActiveTrue();
    }

    public void cancellaSezione(UUID id){

        Sezione sezione = sezioneRepository.findById(id)
            .orElseThrow(() -> new RisorsaNonTrovataException("Sezione non trovata"));
    
        if(!sezione.isActive()){
        throw new RichiestaNonValidaException("La sezione è già stata disattivata");
    }

    sezione.setActive(false);
    sezioneRepository.save(sezione);

    }


    // "Conversione" da dto a Entity = DTO di input -> Entity JPA 
    public Sezione creaSezione(SezioneDTO dto){

        Sezione sezione = new Sezione();

        sezione.setId(UUID.randomUUID());
        sezione.setNome(dto.nome());
        sezione.setDescrizione(dto.descrizione());
        sezione.setActive(true);

        // Passaggio al layer "repository" che si occuperà del salvataggio nel DB
        return sezioneRepository.save(sezione);

    }

}
