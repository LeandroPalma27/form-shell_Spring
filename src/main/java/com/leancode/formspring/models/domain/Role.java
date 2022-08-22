package com.leancode.formspring.models.domain;

import javax.validation.constraints.NotNull;

public class Role {
    
    @NotNull(message = "Seleccione alguna opcion.")
    private Integer id;
    private String codigo;
    private String nombre;

    public Role() {}

    public Role(Integer id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        Role role = (Role)obj;
        return this.id != null && this.id.equals(role.getId());
    }  

}
