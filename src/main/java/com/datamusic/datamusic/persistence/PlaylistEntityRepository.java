package com.datamusic.datamusic.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.repository.PlaylistRepository;
import com.datamusic.datamusic.persistence.crud.PlaylistCrudRepository;
import com.datamusic.datamusic.persistence.entity.PlaylistEntity;
import com.datamusic.datamusic.persistence.mapper.PlaylistMapper;

@Repository
public class PlaylistEntityRepository implements PlaylistRepository{

    @Autowired
    private PlaylistCrudRepository playlistCrudRepository;

    @Autowired
    private PlaylistMapper mapper;

    @Override
    public List<Playlist> getAll() {
        List<PlaylistEntity> playlists=(List<PlaylistEntity>) playlistCrudRepository.findAll();
        return mapper.toPlaylists(playlists);
        
    }

    @Override
    public Optional<Playlist> getPlaylist(Long playlistId) {
        return playlistCrudRepository.findById(playlistId).map(playlist->mapper.toPlaylist(playlist));
    }

    @Override
    public Playlist save(Playlist playlist) {
        PlaylistEntity PlaylistEntity=mapper.toPlaylistEntity(playlist);
        PlaylistEntity.setFechaCreacion(LocalDateTime.now());
        return mapper.toPlaylist(playlistCrudRepository.save(PlaylistEntity));

    }

    @Override
    public void delete(Long playlistId) {
        playlistCrudRepository.deleteById(playlistId);
    }
    
}
