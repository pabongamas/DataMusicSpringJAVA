package com.datamusic.datamusic.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;

@Service
public class AlbumArtistService {

    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    public List<AlbumArtist> getAll() {
        return albumArtistRepository.getAll();
    }

    public List<AlbumArtist> getAlbumArtistByAlbumId(Long idAlbum) {
        return albumArtistRepository.getAlbumArtistByAlbumId(idAlbum);
    }

    public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist) {
        return albumArtistRepository.getAlbumArtistByArtistId(idArtist);
    }

    public AlbumArtist saveAlbumArtist(AlbumArtist albumArtist) {
        return albumArtistRepository.save(albumArtist);
    }

     public boolean delete(Long idAlbum,Long idArtist) {
        albumArtistRepository.delete(idAlbum,idArtist);
        return true;
    }

}
