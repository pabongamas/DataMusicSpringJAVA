package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.Playlist;

public interface PlaylistRepository {

    List<Playlist> getAll();

    Optional<Playlist> getPlaylist(Long playlistId);

    Playlist save(Playlist playlist);
    void delete(Long playlistId);
    List<Playlist> getPlaylistByUser(Long idUser);
}
