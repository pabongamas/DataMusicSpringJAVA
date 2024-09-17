package com.datamusic.datamusic.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * CancionesUsuarios
 */
@Entity
@Table(name = "canciones_usuarios")
public class CancionesUsuarios {

    @EmbeddedId
    private CancionesUsuariosPK id;

    @ManyToOne
    @MapsId("idCancion")
    @JoinColumn(name = "id_cancion", insertable = false, updatable = false)
    private Cancion cancion;


    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable=false,updatable=false)
    private Usuario usuario;


    public CancionesUsuariosPK getId() {
        return id;
    }


    public void setId(CancionesUsuariosPK id) {
        this.id = id;
    }


    public Cancion getCancion() {
        return cancion;
    }


    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    

}