package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Song;

public interface SongRepository {
     List<Song>getAll();
     Page<Song>getAllPageable(Pageable pageable);
     Page<Song>getSongsLikedByUser(Long userId,Pageable pageable);
     Optional<Song>getSong(Long songId);
     List<Song>getSongsByAlbumId(Long albumId);
     List<Song>getSongsByGeneroId(Long generoId);
     List<Song>getSongsByArtistId(Long artistId);
     List<Song> getSongsByPlaylistId(Long playlistId);
     Page<Song>getSongsByPlaylistIdPage(Long playlistId,Pageable pageable);
     Song save(Song song);
     Optional<Song> getSongByNameAndAlbumId(String name,Long albumId);
     void delete(Long songId);
     List<Song>getSongsOfAlbumLikedByUser(Long userId,Long albumId);
     boolean songIsLiked(Long songId,Long userId,Long albumId);
}
