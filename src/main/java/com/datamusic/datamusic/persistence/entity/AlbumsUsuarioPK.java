package com.datamusic.datamusic.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AlbumsUsuarioPK implements Serializable {
    
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_album")
    private Long idAlbum;

    
    public AlbumsUsuarioPK() {
    }

    

    public AlbumsUsuarioPK(Long idUsuario, Long idAlbum) {
        this.idUsuario = idUsuario;
        this.idAlbum = idAlbum;
    }



    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
