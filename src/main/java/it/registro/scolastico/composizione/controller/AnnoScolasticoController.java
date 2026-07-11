package it.registro.scolastico.composizione.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.registro.scolastico.composizione.dto.AnnoScolasticoDTO;
import it.registro.scolastico.composizione.dto.AnnoScolasticoResponseDTO;
import it.registro.scolastico.composizione.model.AnnoScolastico;
import it.registro.scolastico.composizione.service.AnnoScolasticoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/anni-scolastici")
public class AnnoScolasticoController {

    private final AnnoScolasticoService annoScolasticoService;

    public AnnoScolasticoController(AnnoScolasticoService annoScolasticoService) {
        this.annoScolasticoService = annoScolasticoService;
    }

    @PostMapping
    public ResponseEntity<AnnoScolasticoResponseDTO> creaAnnoScolastico(
            @Valid @RequestBody AnnoScolasticoDTO dto) {

        AnnoScolastico annoCreato = annoScolasticoService.creaAnnoScolastico(dto);
        
        // "Conversione" da Entity a DTO
        AnnoScolasticoResponseDTO response = AnnoScolasticoResponseDTO.fromEntity(annoCreato);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Il "path" fin qui è "/api/v1/anni-scolastici"
    @GetMapping("/corrente")
    public ResponseEntity<AnnoScolasticoResponseDTO> cercaAnnoCorrente(){
        AnnoScolastico annoCorrente = annoScolasticoService.cercaAnnoCorrente();

        // "Conversione" da Entity a DTO
        AnnoScolasticoResponseDTO response =  AnnoScolasticoResponseDTO.fromEntity(annoCorrente);

        return ResponseEntity
            .ok()
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaAnnoScolastico(@PathVariable UUID id){
        
        this.annoScolasticoService.cancellaAnnoScolastico(id);
        
        return ResponseEntity.noContent().build();
    }
    
}