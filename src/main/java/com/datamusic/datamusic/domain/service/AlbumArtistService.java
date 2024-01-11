package com.datamusic.datamusic.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;

@Service
public class AlbumArtistService {
    
    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    public List<AlbumArtist> getAll(){
        return albumArtistRepository.getAll();
    }
    public List<AlbumArtist> getAlbumArtistByAlbumId(Long idAlbum){
        return albumArtistRepository.getAlbumArtistByAlbumId(idAlbum);
    }
     public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist){
        return albumArtistRepository.getAlbumArtistByArtistId(idArtist);
    }

    

}
