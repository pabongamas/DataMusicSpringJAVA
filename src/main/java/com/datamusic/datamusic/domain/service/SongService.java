package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.repository.SongRepository;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public List<Song> getAll(){
        return songRepository.getAll();
    }

    public Optional<Song> getSong(Long songId){
        return songRepository.getSong(songId);
    }
    public List<Song> getSongsByAlbumId(Long albumId){
        return songRepository.getSongsByAlbumId(albumId);
    }
    public List<Song> getSongsByGeneroId(Long genderId){
        return songRepository.getSongsByGeneroId(genderId);
    }
    public List<Song> getSongsByArtistId(Long artistId){
        return songRepository.getSongsByArtistId(artistId);
    }
    public Song save(Song song){
        return songRepository.save(song);
    }
    public Optional<Song> getSongByNameAndAlbumId(String name,Long albumId){
        return songRepository.getSongByNameAndAlbumId(name,albumId);
    }
    public boolean delete(Long songId){
        return getSong(songId).map(song->{
            songRepository.delete(songId);
            return true;
        }).orElse(false);
    }

}
