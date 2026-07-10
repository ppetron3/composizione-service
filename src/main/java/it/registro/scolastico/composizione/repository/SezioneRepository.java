package it.registro.scolastico.composizione.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.registro.scolastico.composizione.model.Sezione;

public interface SezioneRepository extends JpaRepository<Sezione, UUID>{

}
