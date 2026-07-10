package it.registro.scolastico.composizione.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "classi")
public class Classe {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anno_scolastico", nullable = false)
    private AnnoScolastico annoScolastico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sezione", nullable = false)
    private Sezione sezione;    

    @Column(name = "id_scuola", nullable = false, updatable = false)
    private UUID idScuola;

    @Column(name = "numero_anno", nullable = false)
    private int numeroAnno;

    @Column(name = "aula", nullable = true, length = 50)
    private String aula;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "creato_il", nullable = false, updatable = false)
    private LocalDateTime creatoIl;

    // Costruttore vuoto esplicito per JPA
    public Classe() {}

    // GETTER E SETTER STANDARD
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AnnoScolastico getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(AnnoScolastico annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public Sezione getSezione() {
        return sezione;
    }

    public void setSezione(Sezione sezione) {
        this.sezione = sezione;
    }

    public UUID getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(UUID idScuola) {
        this.idScuola = idScuola;
    }

    public int getNumeroAnno() {
        return numeroAnno;
    }

    public void setNumeroAnno(int numeroAnno) {
        this.numeroAnno = numeroAnno;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatoIl() {
        return creatoIl;
    }

    public void setCreatoIl(LocalDateTime creatoIl) {
        this.creatoIl = creatoIl;
    }
}