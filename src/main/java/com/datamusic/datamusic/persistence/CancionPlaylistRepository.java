package com.datamusic.datamusic.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.repository.SongPlaylistRepository;
import com.datamusic.datamusic.persistence.crud.CancionPlaylistCrudRepository;
import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.mapper.SongPlaylistMapMapper;

@Repository
public class CancionPlaylistRepository implements SongPlaylistRepository {
    @Autowired
    private CancionPlaylistCrudRepository cancionPlaylistCrudRepository;
    @Autowired
    private SongPlaylistMapMapper mapper;

    @Override
    public List<SongPlaylist> getAll() {
        List<CancionPlaylist>cancionPlaylists=(List<CancionPlaylist>) cancionPlaylistCrudRepository.findAll();
        return  mapper.toSongPlaylistMap(cancionPlaylists);
    }
    
}
