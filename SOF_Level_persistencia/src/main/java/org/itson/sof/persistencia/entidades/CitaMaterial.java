package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cita_material")
public class CitaMaterial implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;
    
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    @Column(name = "cantidad", nullable = false)
    private float cantidad;
    
    public CitaMaterial() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cita getCita() {
        return cita;
    }
    
    public void setCita(Cita cita) {
        this.cita = cita;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CitaMaterial{" + "id=" + id + ", cita=" + cita.getId() + ", material=" + material.getId() + ", cantidad=" + cantidad + '}';
    }
    
    

    
    
    
}
