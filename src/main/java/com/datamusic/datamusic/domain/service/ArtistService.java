package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.repository.ArtistRepository;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository ArtistRepository;


     public List<Artist> getAll() {
        return ArtistRepository.getAll();
    }
    public Page<Artist>getAllPageable(int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable=PageRequest.of(page, elements, sort);
        return ArtistRepository.getAllPageable(pageable);
    }
    
    public List<Artist> geArtistsByName(String name) {
        return ArtistRepository.getArtistsByName(name);
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
