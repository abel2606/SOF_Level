package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaqueteTipoFoto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paquete_id")
    private Paquete paquete;

    @ManyToOne
    @JoinColumn(name = "tipo_foto_id")
    private TipoFoto tipoFoto;

    @Column(name = "cantidad")
    private int cantidad; 

    public PaqueteTipoFoto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public TipoFoto getTipoFoto() {
        return tipoFoto;
    }

    public void setTipoFoto(TipoFoto tipoFoto) {
        this.tipoFoto = tipoFoto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

