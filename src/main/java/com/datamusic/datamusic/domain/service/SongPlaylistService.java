package com.datamusic.datamusic.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.repository.SongPlaylistRepository;

import java.util.Iterator;
import java.util.List;

@Service
public class SongPlaylistService {
   
    @Autowired
    private SongPlaylistRepository songPlaylistRepository;


    public List<SongPlaylist> getAll(){
        List<SongPlaylist> songPlaytlistALL= songPlaylistRepository.getAll();
        Iterator<SongPlaylist> iteratorSongPlaylist=songPlaytlistALL.iterator();
        while (iteratorSongPlaylist.hasNext()) {
            SongPlaylist songPlaylistIterator=iteratorSongPlaylist.next();
           UserEntity user= songPlaylistIterator.getPlaylist().getUser();
           user.setPassword(null);
           user.setRols(null);
        }
        return songPlaytlistALL;
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
