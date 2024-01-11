package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.repository.AlbumRepository;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAll() {
        return albumRepository.getAll();
    }

    public Optional<Album>getAlbumById(Long albumId){
        return albumRepository.getAlbumById(albumId);
    }
    public List<Album>getAlbumByGenderId(Long genderId){
        return albumRepository.getAlbumsByGender(genderId);
    }
      public Album save(Album album) {
        return albumRepository.save(album);
    }
    public boolean delete(Long albumId){
        return getAlbumById(albumId).map(album->{
            albumRepository.delete(albumId);
            return true;
        }).orElse(false);
    }
}
