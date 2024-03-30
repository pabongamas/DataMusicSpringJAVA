package com.datamusic.datamusic.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_roles")
public class UsuarioRol {
    @EmbeddedId
    private UsuarioRolPK id;


    
    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    private RolEntity rol;

    public UsuarioRolPK getId() {
        return id;
    }

    public void setId(UsuarioRolPK id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RolEntity getRol() {
        return rol;
    }

    public void setRol(RolEntity rol) {
        this.rol = rol;
    }


}
