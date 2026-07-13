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

@RestController
@RequestMapping("/api/v1/classi")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    public ResponseEntity<ClasseResponseDTO> creaClasse(@Valid @RequestBody ClasseDTO dto) {
        Classe classeCreata = classeService.creaClasse(dto);

        ClasseResponseDTO response = ClasseResponseDTO.fromEntity(classeCreata);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponseDTO> cercaPerId(@PathVariable UUID id) {
        Classe classeCercata = classeService.cercaClassePerId(id);

        ClasseResponseDTO response = ClasseResponseDTO.fromEntity(classeCercata);

        return ResponseEntity.ok(response);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaClasse(@PathVariable UUID id) {
        classeService.cancellaClasse(id);
        return ResponseEntity.noContent().build();
    }
}