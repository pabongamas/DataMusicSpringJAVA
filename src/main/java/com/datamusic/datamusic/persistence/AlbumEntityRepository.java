package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.repository.AlbumRepository;
import com.datamusic.datamusic.persistence.crud.AlbumCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.mapper.AlbumMapper;

@Repository
public class AlbumEntityRepository implements AlbumRepository {

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    @Autowired
    private AlbumMapper mapper;

    @Override
    public List<Album> getAll() {
        List<AlbumEntity> albums = (List<AlbumEntity>) albumCrudRepository.findAll();
        return mapper.toAlbum(albums);
    }

    @Override
    public Optional<Album> getAlbumById(Long albumId) {
        return albumCrudRepository.findById(albumId).map(album->mapper.toAlbum(album));
    }

    @Override
    public Album save(Album album) {
       AlbumEntity albumEntity=mapper.toAlbumEntity(album);
       return mapper.toAlbum(albumCrudRepository.save(albumEntity));
    }

    @Override
    public void delete(Long albumId) {
       albumCrudRepository.deleteById(albumId);
    }
    

}
