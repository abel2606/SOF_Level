
package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Compra implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folio;
    
    @Column(name = "cantidad")
    private Float cantidad;
    
    @Column(name = "fecha_hora")
    private GregorianCalendar fechaHora;
    
    @Column(name = "costo")
    private Double costo;
    
    @Column(name = "proveedor")
    private String proveedor;
    
    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
    
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    public Compra() {}
    
    public Long getFolio() {
        return folio;
    }
    
    public void setFolio(Long folio) {
        this.folio = folio;
    }
    
    public Float getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
    
    public GregorianCalendar getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(GregorianCalendar fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public Double getCosto() {
        return costo;
    }
    
    public void setCosto(Double costo) {
        this.costo = costo;
    }
    
    public String getProveedor() {
        return proveedor;
    }
    
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    public Contrato getContrato() {
        return contrato;
    }
    
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Compra{" + "folio=" + folio + ", cantidad=" + cantidad + ", fechaHora=" + fechaHora + ", costo=" + costo + ", proveedor=" + proveedor + ", contrato=" + contrato.getFolio() + ", material=" + material.getNombre() + '}';
    }
    
    
}