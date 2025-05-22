package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Contrato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tematica")
    private String tematica;

    @Column(name = "estado")
    private String estado;

    @Column(name = "folio")
    private String folio;
    
    @Column(name = "fecha_inicio")
    private GregorianCalendar fechaInicio;

    @Column(name = "fecha_termino")
    private GregorianCalendar fechaTermino;
    
    @ManyToOne
    @JoinColumn(name = "paquete_id")
    private Paquete paquete;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "contrato")
    private Set<Compra> compras = new HashSet<>();

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    public Contrato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public GregorianCalendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(GregorianCalendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public GregorianCalendar getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(GregorianCalendar fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contrato{");
        sb.append("id=").append(id);
        sb.append(", tematica=").append(tematica);
        sb.append(", estado=").append(estado);
        sb.append(", folio=").append(folio);
        sb.append(", fechaInicio=").append(fechaInicio);
        sb.append(", fechaTermino=").append(fechaTermino);
        sb.append(", paquete=").append(paquete);
        sb.append(", cliente=").append(cliente);
        sb.append(", compras=").append(compras);
        sb.append(", citas=").append(citas);
        sb.append('}');
        return sb.toString();
    }
    
    

    

}
