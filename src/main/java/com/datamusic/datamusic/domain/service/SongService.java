package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Song> getAllPageable(int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable=PageRequest.of(page, elements, sort);
        return songRepository.getAllPageable(pageable);
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
    public List<Song> getSongsByPlaylist(Long idPlaylist){
        return songRepository.getSongsByPlaylistId(idPlaylist);
    }
    public Page<Song> getSongsByPlaylistPage(Long idPlaylist, int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageRequest=PageRequest.of(page,elements,sort);
        return songRepository.getSongsByPlaylistIdPage(idPlaylist,pageRequest);
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
