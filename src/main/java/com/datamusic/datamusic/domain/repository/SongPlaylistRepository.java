package com.datamusic.datamusic.domain.repository;

import java.util.List;

import com.datamusic.datamusic.domain.SongPlaylist;

public interface SongPlaylistRepository {
    List<SongPlaylist> getAll();

    List<SongPlaylist> getSongPlaylistBySongId(Long songId);

    List<SongPlaylist> getSongPlaylistByPlaylistId(Long playlistId);

    SongPlaylist save(SongPlaylist SongPlaylist);
} 
