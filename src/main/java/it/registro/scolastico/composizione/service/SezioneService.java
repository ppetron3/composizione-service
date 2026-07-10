package it.registro.scolastico.composizione.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.registro.scolastico.composizione.dto.SezioneDTO;
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
    public Optional<Sezione> cercaPerId(UUID id){
        return this.sezioneRepository.findById(id);
    }

    // Metodo per recuperare TUTTE le sezioni
    public List<Sezione> cercaTutti(){
        return this.sezioneRepository.findAll();
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
