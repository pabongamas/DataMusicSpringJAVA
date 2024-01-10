package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.Album;

public interface  AlbumRepository {
    List<Album>getAll();
    Optional<Album>getAlbumById(Long albumId);
    Album save(Album album);
}
