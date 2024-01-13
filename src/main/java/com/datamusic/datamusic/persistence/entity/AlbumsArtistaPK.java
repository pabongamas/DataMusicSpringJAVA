package com.datamusic.datamusic.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AlbumsArtistaPK implements Serializable {
    @Column(name = "id_album")
    private Long idAlbum;

    @Column(name = "id_artista")
    private Long idArtista;

    // se requiere este constructor vacio para

    // En Hibernate, cuando trabajas con clases embeddables, estas clases deben
    // tener un constructor sin argumentos (un constructor predeterminado)
    // para que Hibernate pueda crear instancias de ellas mediante reflexión. Si la
    // clase embeddable AlbumsArtistaPK no tiene un constructor sin argumentos,
    // recibirás este tipo de error.
    public AlbumsArtistaPK() {
    }

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
