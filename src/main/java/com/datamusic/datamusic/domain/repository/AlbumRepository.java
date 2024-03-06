package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Album;

public interface  AlbumRepository {
    List<Album>getAll();
    Optional<Album>getAlbumById(Long albumId);
    Album save(Album album);
    void delete(Long albumId);
    List<Album>getAlbumsByGender(Long genderId);
    Page<Album>getAllByPage(Pageable pageable);
    Page<Album>getAlbumsByGenderByPage(Long genderId,Pageable pageable);
}
