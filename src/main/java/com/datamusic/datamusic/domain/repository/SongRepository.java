package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Song;

public interface SongRepository {
     List<Song>getAll();
     Page<Song>getAllPageable(Pageable pageable);
     Optional<Song>getSong(Long songId);
     List<Song>getSongsByAlbumId(Long albumId);
     List<Song>getSongsByGeneroId(Long generoId);
     List<Song>getSongsByArtistId(Long artistId);
     Song save(Song song);
     Optional<Song> getSongByNameAndAlbumId(String name,Long albumId);
     void delete(Long songId);



}
