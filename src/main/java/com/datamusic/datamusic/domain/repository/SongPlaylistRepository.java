package com.datamusic.datamusic.domain.repository;

import java.util.List;

import com.datamusic.datamusic.domain.SongPlaylist;

public interface SongPlaylistRepository {
    List<SongPlaylist> getAll();
}
