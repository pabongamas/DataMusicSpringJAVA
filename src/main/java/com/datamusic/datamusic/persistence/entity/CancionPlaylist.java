package com.datamusic.datamusic.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "canciones_playlists")
public class CancionPlaylist {

    @EmbeddedId
    private CancionPlaylistPK id;

    @ManyToOne
    @MapsId("idCancion")
    @JoinColumn(name = "id_cancion", insertable = false, updatable = false)
    private Cancion cancion;

    @ManyToOne
    @MapsId("idPlaylist")
    @JoinColumn(name = "id_playlist", insertable = false, updatable = false)
    private PlaylistEntity playlistEntity;

    public CancionPlaylistPK getId() {
        return id;
    }

    public void setId(CancionPlaylistPK id) {
        this.id = id;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public PlaylistEntity getPlaylistEntity() {
        return playlistEntity;
    }

    public void setPlaylistEntity(PlaylistEntity playlistEntity) {
        this.playlistEntity = playlistEntity;
    }

}
