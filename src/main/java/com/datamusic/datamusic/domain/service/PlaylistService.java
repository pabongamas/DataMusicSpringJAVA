package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.repository.PlaylistRepository;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAll() {
        return playlistRepository.getAll();
    }

    public Optional<Playlist> getPlaylistById(Long playlistId) {
        return playlistRepository.getPlaylist(playlistId);
    }

    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public boolean delete(Long PlaylistId) {
        return getPlaylistById(PlaylistId).map(playlist -> {
            playlistRepository.delete(PlaylistId);
            return true;
        }).orElse(false);
    }

    public List<Playlist> getPlaylistsByUser(Long idUser) {
        return playlistRepository.getPlaylistByUser(idUser);
    }

}
