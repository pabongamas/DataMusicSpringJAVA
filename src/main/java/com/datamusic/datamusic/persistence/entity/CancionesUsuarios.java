package com.datamusic.datamusic.persistence.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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

    @Column(name = "fecha_agregada")
    private LocalDateTime fecha_agregada;


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


    public LocalDateTime getFecha_agregada() {
        return fecha_agregada;
    }


    public void setFecha_agregada(LocalDateTime fecha_agregada) {
        this.fecha_agregada = fecha_agregada;
    }
}