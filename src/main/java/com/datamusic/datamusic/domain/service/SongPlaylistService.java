package com.datamusic.datamusic.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.repository.SongPlaylistRepository;

import java.util.List;

@Service
public class SongPlaylistService {
   
    @Autowired
    private SongPlaylistRepository songPlaylistRepository;


    public List<SongPlaylist> getAll(){
        return songPlaylistRepository.getAll();
    }
    public List<SongPlaylist> getSongPlaylistBySongId(Long songId){
        return songPlaylistRepository.getSongPlaylistBySongId(songId);
    }
    public List<SongPlaylist> getSongPlaylistByPlaylistId(Long playlistId){
        return songPlaylistRepository.getSongPlaylistByPlaylistId(playlistId);
    }
    public SongPlaylist saveSongPlaylist(SongPlaylist songPlaylist) {
        return songPlaylistRepository.save(songPlaylist);
    }
}
