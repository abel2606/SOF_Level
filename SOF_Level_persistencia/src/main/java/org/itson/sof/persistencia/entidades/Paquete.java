package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@Entity
public class Paquete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaqueteTipoFoto> tiposDeFoto = new HashSet<>();

    @OneToMany(mappedBy = "paquete")
    private Set<Contrato> contratos = new HashSet<>();

    public Paquete() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<PaqueteTipoFoto> getTiposDeFoto() {
        return tiposDeFoto;
    }

    public void setTiposDeFoto(Set<PaqueteTipoFoto> tiposDeFoto) {
        this.tiposDeFoto = tiposDeFoto;
    }

    public Set<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public String toString() {
        return "Paquete{" + "id=" + id + ", precio=" + precio + ", nombre=" + nombre + ", tiposDeFoto=" + tiposDeFoto + '}';
    }
    
    
}
