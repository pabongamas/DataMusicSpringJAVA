package com.datamusic.datamusic.persistence.entity;

// import java.util.List;

// import com.datamusic.datamusic.domain.Album;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums_artista")
public class AlbumsArtista {

    @EmbeddedId
    private AlbumsArtistaPK id;

    @ManyToOne
    @MapsId("idAlbum")
    @JoinColumn(name = "id_album", insertable = false, updatable = false)
    private AlbumEntity album;

    @ManyToOne
    @MapsId("idArtista")
    @JoinColumn(name = "id_artista", insertable = false, updatable = false)
    private Artista artista;
    
    public AlbumsArtistaPK getId() {
        return id;
    }

    public void setId(AlbumsArtistaPK id) {
        this.id = id;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    

}
