package datos.mapeo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("FOTOGRAFO")
public class Fotografo extends Usuario implements Serializable{
    
    @Column(name = "correo")
    private String correo;
    
    @Column(name = "nombre_persona")
    private String nombrePersona;
    
    @Column(name = "telefono")
    private String telefono;
    
    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cita> citas = new HashSet<>();

    public Fotografo() {
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

}
