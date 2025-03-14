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
public class TipoFoto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "altura")
    private Float altura;
    
    @Column(name = "anchura")
    private Float anchura;

    @Column(name = "marco")
    private String marco;
    
    @OneToMany(mappedBy = "tipoFoto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaqueteTipoFoto> paquetes = new HashSet<>();

    public TipoFoto() {
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

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getAnchura() {
        return anchura;
    }

    public void setAnchura(Float anchura) {
        this.anchura = anchura;
    }

    public String getMarco() {
        return marco;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public Set<PaqueteTipoFoto> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(Set<PaqueteTipoFoto> paquetes) {
        this.paquetes = paquetes;
    }
    
}
