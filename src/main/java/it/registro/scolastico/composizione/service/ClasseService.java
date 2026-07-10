package it.registro.scolastico.composizione.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.registro.scolastico.composizione.dto.ClasseDTO;
import it.registro.scolastico.composizione.exception.ConflittoException;
import it.registro.scolastico.composizione.exception.RisorsaNonTrovataException;
import it.registro.scolastico.composizione.model.AnnoScolastico;
import it.registro.scolastico.composizione.model.Classe;
import it.registro.scolastico.composizione.model.Sezione;
import it.registro.scolastico.composizione.repository.AnnoScolasticoRepository;
import it.registro.scolastico.composizione.repository.ClasseRepository;
import it.registro.scolastico.composizione.repository.SezioneRepository;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;
    private final AnnoScolasticoRepository annoScolasticoRepository;
    private final SezioneRepository sezioneRepository;

    public ClasseService(ClasseRepository classeRepository, 
                         AnnoScolasticoRepository annoScolasticoRepository, 
                         SezioneRepository sezioneRepository) {
        this.classeRepository = classeRepository;
        this.annoScolasticoRepository = annoScolasticoRepository;
        this.sezioneRepository = sezioneRepository;
    }

    public Classe creaClasse(ClasseDTO dto) {

        // Recuperiamo l'anno scolastico dal DB, verificando che ci sia 
        AnnoScolastico anno = annoScolasticoRepository.findById(dto.idAnnoScolastico())
            .orElseThrow(() -> new RisorsaNonTrovataException("Anno scolastico non trovato"));

            if(!anno.isActive()){
                throw new ConflittoException("L'anno scolastico esiste ma non è attivo");
            }

        Sezione sezione = sezioneRepository.findById(dto.idSezione())
            .orElseThrow(() -> new RisorsaNonTrovataException("Sezione non trovata"));

            if(!sezione.isActive()){
                throw new ConflittoException("La sezione esiste ma non è attiva ");
            }

        if(classeRepository.findByIdScuolaAndNumeroAnnoAndSezioneIdAndAnnoScolasticoId(
            dto.idScuola(),
            dto.numeroAnno(), 
            dto.idSezione(), 
            dto.idAnnoScolastico()).isPresent()){
        // Costruzione dell'Entity con i metodi generati a compile-time da Lombok
            throw new ConflittoException(
                "Esiste già una classe con la stessa scuola, numero anno, sezione e anno scolastico"
            );
            }
     
        Classe nuovaClasse = new Classe();
        nuovaClasse.setId(UUID.randomUUID());
        nuovaClasse.setAnnoScolastico(anno);
        nuovaClasse.setSezione(sezione);
        nuovaClasse.setIdScuola(dto.idScuola());
        nuovaClasse.setNumeroAnno(dto.numeroAnno());
        nuovaClasse.setAula(dto.aula());
        nuovaClasse.setActive(true);
        nuovaClasse.setCreatoIl(LocalDateTime.now());

        return classeRepository.save(nuovaClasse);

    }



    public Classe cercaClassePerId(UUID id){
        
        return this.classeRepository.findById(id)
               .orElseThrow(() -> new RisorsaNonTrovataException("Classe non trovata"));
    }

    
    public List<Classe> cercaClassiPerAnnoScolastico(UUID idAnnoScolastico){

        AnnoScolastico anno = this.annoScolasticoRepository.findById(idAnnoScolastico)
            .orElseThrow(() -> new RisorsaNonTrovataException("Anno scolastico non trovato"));

        if(!anno.isActive()){
            throw new ConflittoException("Anno trovato ma non attivo");
        }

        return this.classeRepository.findByAnnoScolasticoIdAndActiveTrue(idAnnoScolastico);
    }
}