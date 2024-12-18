package com.datamusic.datamusic.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums_usuarios")
public class AlbumsUsuario {

    @EmbeddedId
    private AlbumsUsuarioPK id;

    @ManyToOne
    @MapsId("idAlbum")
    @JoinColumn(name = "id_album", insertable = false, updatable = false)
    private AlbumEntity album;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "fecha_agregada")
    private LocalDateTime fecha_agregada;

    

    public AlbumsUsuarioPK getId() {
        return id;
    }

    public void setId(AlbumsUsuarioPK id) {
        this.id = id;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
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
