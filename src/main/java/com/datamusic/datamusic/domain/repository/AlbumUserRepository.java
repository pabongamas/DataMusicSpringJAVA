package com.datamusic.datamusic.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.AlbumUser;
import com.datamusic.datamusic.domain.SongUser;

public interface AlbumUserRepository {
        List<AlbumUser> getAll();

        Page<AlbumUser> getAllByPage(Pageable pageable);

        List<AlbumUser> likedAlbumsByUser(Long userId);

        AlbumUser likedAlbum(Long userId, Long albumId);

        boolean dislikeAlbum(Long userId, Long albumId);

}
