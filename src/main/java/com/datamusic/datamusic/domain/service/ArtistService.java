package com.datamusic.datamusic.domain.service;

import java.util.List;

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
}
