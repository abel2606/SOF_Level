package org.itson.sof.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario implements Serializable {
    
    public Administrador() {
    }
}
