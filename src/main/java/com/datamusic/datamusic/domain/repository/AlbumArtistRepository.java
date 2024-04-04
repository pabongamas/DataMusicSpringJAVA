package com.datamusic.datamusic.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.AlbumArtist;

public interface AlbumArtistRepository {
    List<AlbumArtist> getAll();

    Page<AlbumArtist> getAllByPage(Pageable pageable);

    List<AlbumArtist> getAlbumArtistByAlbumId(Long idAlbum);

    List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist);

    List<AlbumArtist> getAlbumArtistByAlbumIdAndArtistId(Long idAlbum, Long idArtist);

    AlbumArtist save(AlbumArtist albumArtist);

    void delete(Long idAlbum, Long idArtist);

}
