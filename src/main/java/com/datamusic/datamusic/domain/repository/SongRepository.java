package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.Song;

public interface SongRepository {
     List<Song>getAll();
     Optional<Song>getSong(Long songId);
     List<Song>getSongsByAlbumId(Long albumId);
     List<Song>getSongsByGeneroId(Long generoId);
     List<Song>getSongsByArtistId(Long artistId);
     Song save(Song song);
     Optional<Song> songByName(String name,Long albumId);



}
