package com.datamusic.datamusic.domain.repository;

import java.util.List;

import com.datamusic.datamusic.domain.SongUser;

public interface SongUserRepository {
    List<SongUser> getAll();
    SongUser likedSong(Long userId,Long songId);
    boolean dislikeSong(Long userId,Long songId);
}
