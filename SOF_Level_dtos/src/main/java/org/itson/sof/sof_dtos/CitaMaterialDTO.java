/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.sof_dtos;

/**
 *
 * @author Abel
 */
public class CitaMaterialDTO {
    private Long id;
    private MaterialDTO material;
    private float cantidad;

    public CitaMaterialDTO() {}

    public CitaMaterialDTO(Long id, MaterialDTO material, int cantidad) {
        this.id = id;
        this.material = material;
        this.cantidad = cantidad;
    }

    public CitaMaterialDTO(MaterialDTO material, float cantidad) {
        this.material = material;
        this.cantidad = cantidad;
    }
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CitaMaterialDTO{" +
                "id=" + id +
                ", material=" + material +
                ", cantidad=" + cantidad +
                '}';
    }
}

