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
import org.springframework.web.bind.annotation.RestController;

import it.registro.scolastico.composizione.dto.SezioneDTO;
import it.registro.scolastico.composizione.dto.SezioneResponseDTO;
import it.registro.scolastico.composizione.model.Sezione;
import it.registro.scolastico.composizione.service.SezioneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sezioni")
public class SezioneController {

    private final SezioneService sezioneService;

    public SezioneController(SezioneService sezioneService){
        this.sezioneService = sezioneService;
    }

    // POST -> Controller riceve DTO -> Service crea Entity -> Controller converte in ResponseDTO -> 201 created
    @PostMapping
    public ResponseEntity<SezioneResponseDTO> creaSezione(@Valid @RequestBody SezioneDTO dto){

        Sezione sezioneCreata = sezioneService.creaSezione(dto);

        SezioneResponseDTO response = SezioneResponseDTO.fromEntity(sezioneCreata);

        return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(response); 

    }
    
    @GetMapping
    public ResponseEntity<List<SezioneResponseDTO>> cercaTutti() {
        List<Sezione> sezioni = sezioneService.cercaTutti();

        List<SezioneResponseDTO> sezioniDTO = new ArrayList<>();

        for (Sezione s : sezioni) {
            sezioniDTO.add(SezioneResponseDTO.fromEntity(s));
        }

        return ResponseEntity.ok(sezioniDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaSezione(@PathVariable UUID id){
        this.sezioneService.cancellaSezione(id);
        return ResponseEntity.noContent().build();
    }

}
