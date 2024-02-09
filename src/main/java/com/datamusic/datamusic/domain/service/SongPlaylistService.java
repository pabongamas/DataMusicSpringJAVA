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
}
