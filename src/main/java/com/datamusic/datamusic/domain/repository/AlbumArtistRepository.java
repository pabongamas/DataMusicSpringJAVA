package com.datamusic.datamusic.domain.repository;

import java.util.List;

import com.datamusic.datamusic.domain.AlbumArtist;

public interface AlbumArtistRepository {
    List<AlbumArtist>getAll();
    List<AlbumArtist>getAlbumArtistByAlbumId(Long idAlbum);
    List<AlbumArtist>getAlbumArtistByArtistId(Long idArtist);
    AlbumArtist save(AlbumArtist albumArtist);
    void delete(Long idAlbum,Long idArtist);

}
