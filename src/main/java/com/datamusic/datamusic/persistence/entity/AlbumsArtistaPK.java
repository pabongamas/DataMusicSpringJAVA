package com.datamusic.datamusic.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AlbumsArtistaPK implements Serializable {
    @Column(name="id_album")
    private Long idAlbum;

    @Column(name="id_artista")
    private Long idArtista;

    

    public AlbumsArtistaPK(Long idAlbum, Long idArtista) {
        this.idAlbum = idAlbum;
        this.idArtista = idArtista;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }
    
}
