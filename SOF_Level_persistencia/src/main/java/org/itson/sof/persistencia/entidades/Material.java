package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Material implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "cantidad")
    private Float cantidad;
    
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compra> compras = new HashSet<>();
    
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CitaMaterial> citaMateriales = new HashSet<>();

    public Material() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public Set<CitaMaterial> getCitaMateriales() {
        return citaMateriales;
    }

    public void setCitaMateriales(Set<CitaMaterial> citaMateriales) {
        this.citaMateriales = citaMateriales;
    }

    @Override
    public String toString() {
        return "Material{" + "id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", compras=" + compras + ", citaMateriales=" + citaMateriales + '}';
    }

    

    
}