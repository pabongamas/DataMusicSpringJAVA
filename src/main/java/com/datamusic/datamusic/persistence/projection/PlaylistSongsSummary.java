package com.datamusic.datamusic.persistence.projection;

import com.datamusic.datamusic.persistence.entity.AlbumEntity;

public interface PlaylistSongsSummary {
    Long getId_Cancion();

    String getNombre();

    String getDuracion();

    Long getId_Album();

    Long getId_Genero();

    String getNombre_Album();



    
}
