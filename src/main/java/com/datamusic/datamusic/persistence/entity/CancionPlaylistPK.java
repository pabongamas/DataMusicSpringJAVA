package com.datamusic.datamusic.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CancionPlaylistPK implements Serializable {

    @Column(name = "id_cancion")
    private Long idCancion;

    @Column(name = "id_playlist")
    private Long idPlaylist;

    public CancionPlaylistPK() {
    }

    public CancionPlaylistPK(Long idCancion, Long idPlaylist) {
        this.idCancion = idCancion;
        this.idPlaylist = idPlaylist;
    }

    public Long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Long idCancion) {
        this.idCancion = idCancion;
    }

    public Long getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Long idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

}
