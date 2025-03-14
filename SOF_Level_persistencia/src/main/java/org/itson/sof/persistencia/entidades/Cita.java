
package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Cita implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_hora_inicio")
    private GregorianCalendar fechaHoraInicio;
    
    @Column(name = "fecha_hora_fin")
    private GregorianCalendar fechaHoraFin;
    
    @Column(name = "lugar")
    private String lugar;
    
    @Column(name = "extras")
    private String extras;
    
    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
    
    @ManyToOne
    @JoinColumn(name = "fotografo_id")
    private Fotografo fotografo;

    @ManyToMany
    @JoinTable(
            name = "cita_material",
            joinColumns = @JoinColumn(name = "cita_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private Set<Material> materiales = new HashSet<>();

    public Cita() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GregorianCalendar getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(GregorianCalendar fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public GregorianCalendar getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(GregorianCalendar fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Set<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(Set<Material> materiales) {
        this.materiales = materiales;
    }

    public Fotografo getFotografo() {
        return fotografo;
    }

    public void setFotografo(Fotografo fotografo) {
        this.fotografo = fotografo;
    }

}
