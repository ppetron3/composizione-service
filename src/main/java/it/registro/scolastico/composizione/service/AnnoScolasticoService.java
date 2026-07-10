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
        return this.annoScolasticoRepository.findById(id);
    }

    public List<AnnoScolastico> cercaTutti() {
        return this.annoScolasticoRepository.findAll();
    }

    public AnnoScolastico cercaAnnoCorrente(){
    
        return annoScolasticoRepository.findByAnnoCorrenteTrueAndActiveTrue()
            .orElseThrow(() -> new RisorsaNonTrovataException(
                "Anno scolastico corrente non trovato"));
    
    }

}

    