package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.projection.SummaryPlaylistSong;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

public interface PlaylistRepository {

    List<Playlist> getAll();

    Optional<Playlist> getPlaylist(Long playlistId);

    Playlist save(Playlist playlist);
    void delete(Long playlistId);
    List<Playlist> getPlaylistByUser(Long idUser);
    List<SummaryPlaylistSong> getSongs(Long idPlaylist);
    Page<PlaylistSongsSummary> getSongsByPage(Long idPlaylist,Pageable pageable);
}
