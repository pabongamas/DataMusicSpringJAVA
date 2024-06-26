package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;
import com.datamusic.datamusic.domain.repository.AlbumRepository;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    public List<Album> getAll() {
        return albumRepository.getAll();
    }

    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.getAlbumById(albumId);
    }

    public List<Album> getAlbumByGenderId(Long genderId) {
        return albumRepository.getAlbumsByGender(genderId);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }

    public boolean delete(Long albumId) {
        return getAlbumById(albumId).map(album -> {
            // List<AlbumArtist> artistsByAlbum =
            // albumArtistRepository.getAlbumArtistByAlbumId(albumId);
            // if (!artistsByAlbum.isEmpty()) {
            // throw new ResponseStatusException(
            // HttpStatus.CONFLICT, "El album " + album.getName()
            // + " esta siendo ocupado por algun artista , por lo cual no puede ser
            // eliminado.");
            // }
            albumRepository.delete(albumId);
            return true;
        }).orElse(false);
    }

    public List<AlbumArtist> getartistsByAlbum(Long albumId) {
        List<AlbumArtist> artistsByAlbum = albumArtistRepository.getAlbumArtistByAlbumId(albumId);
        return artistsByAlbum;
    }
    // pageable

    public Page<Album> getAllByPage(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return albumRepository.getAllByPage(pageRequest);
    }

    public Page<Album> getAlbumsByGenderByPage(Long genderId,int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return albumRepository.getAlbumsByGenderByPage(genderId,pageRequest);
    }
}
