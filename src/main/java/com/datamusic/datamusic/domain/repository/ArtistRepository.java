package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Artist;

public interface ArtistRepository {
    List<Artist>getAll();
    Page<Artist>getAllPageable(Pageable pageable);
    List<Artist>getArtistsByName(String name);
    Optional<Artist>getArtist(Long artistId);
    Artist save(Artist artist);
    void delete(Long artistId);
}
