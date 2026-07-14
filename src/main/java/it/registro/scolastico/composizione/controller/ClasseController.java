package it.registro.scolastico.composizione.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.registro.scolastico.composizione.dto.ClasseDTO;
import it.registro.scolastico.composizione.dto.ClasseResponseDTO;
import it.registro.scolastico.composizione.model.Classe;
import it.registro.scolastico.composizione.service.ClasseService;
import jakarta.validation.Valid;

/**
 * 
 * ClasseController
 * 
 * Espone gli endpoint REST per la risorsa CLASSE
 * 
 * Riceve richieste HTTP dal client, delega la logica applicativa 
 * al ClasseService e restituisce DTO con status HTTP coerenti
 */

@RestController
@RequestMapping("/api/v1/classi")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }
    /**
     * Crea una nuova classe scolastica a partire dai dati inviati dal client
     * 
     * Il body JSON della richiesta viene deserializzato in un ClasseDTO
     * validato tramiete @Valid e poi passato al service, che esegue
     * i controlli di business e salva l'entità nel database
     * 
     * @param dto dati di input necessari alla creazione della classe
     * @return risposta HTTP 201 con il DTO della classe creata
     */
    @PostMapping
    public ResponseEntity<ClasseResponseDTO> creaClasse(@Valid @RequestBody ClasseDTO dto) {
        Classe classeCreata = classeService.creaClasse(dto);

        ClasseResponseDTO response = ClasseResponseDTO.fromEntity(classeCreata);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Restituisce la classe attiva identificata dall'id passato nel path
     * 
     * @param id identificativo UUID della classe
     * @return risposta HTTP 200 con il DTO della classe trovata
     */

    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponseDTO> cercaPerId(@PathVariable UUID id) {
        Classe classeCercata = classeService.cercaClassePerId(id);

        ClasseResponseDTO response = ClasseResponseDTO.fromEntity(classeCercata);

        return ResponseEntity.ok(response);
    }

    /**
     * Restituisce l'elenco delle classi attive associate a un dato anno scolastico
     * 
     * Il parametro viene letto dalla query string della richiesta HTTP
     * 
     * @param idAnnoScolastico identificativo UUID dell'anno scolastico da usare come filtro
     * @return risposta HTTP 200 con la lista dei DTO delle classi trovate
     */
    @GetMapping
    public ResponseEntity<List<ClasseResponseDTO>> cercaClassiPerAnnoScolastico(
            @RequestParam("id_anno_scolastico") UUID idAnnoScolastico) {

        List<Classe> classiTrovate = classeService.cercaClassiPerAnnoScolastico(idAnnoScolastico);

        List<ClasseResponseDTO> response = new ArrayList<>();

        for (Classe c : classiTrovate) {
            response.add(ClasseResponseDTO.fromEntity(c));
        }

        return ResponseEntity.ok(response);
    }


    /**
     * Esegue la cancellazione logica di una classe scolastica.
     * 
     * Il controller espone l'operazione come DELETE HTTP,
     * mentre il service implementa il soft delete impostando active = false
     * 
     * @param id identificativo UUID della classe da disattivare
     * @return risposta HTTP 204 senza body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaClasse(@PathVariable UUID id) {
        classeService.cancellaClasse(id);
        return ResponseEntity.noContent().build();
    }
}