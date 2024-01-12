package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.Artist;

public interface ArtistRepository {
    List<Artist>getAll();
    List<Artist>getArtistsByName(String name);
    Optional<Artist>getArtist(Long artistId);
    Artist save(Artist artist);
    void delete(Long artistId);
}
