package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.repository.ArtistRepository;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository ArtistRepository;


     public List<Artist> getAll() {
        return ArtistRepository.getAll();
    }
    public Optional<Artist> getArtistById(Long artistId){
        return ArtistRepository.getArtist(artistId);
    }
    public Artist save(Artist artist){
        return ArtistRepository.save(artist);
    }
    public boolean delete(Long artistId){
        return getArtistById(artistId).map(artist->{
            ArtistRepository.delete(artistId);
            return true;
        }).orElse(false);
    }
}
