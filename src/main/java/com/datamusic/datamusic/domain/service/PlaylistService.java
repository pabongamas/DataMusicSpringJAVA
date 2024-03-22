package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.projection.SummaryPlaylistSong;
import com.datamusic.datamusic.domain.repository.PlaylistRepository;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

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

    public List<SummaryPlaylistSong> getSongs(Long idPlaylist){
        return playlistRepository.getSongs(idPlaylist);
    }

    public Page<PlaylistSongsSummary> getSongsPageable(Long idPlaylist,int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest=PageRequest.of(page,elements,sort);
        return playlistRepository.getSongsByPage(idPlaylist, pageRequest);
    }


    public Page<Playlist> getPlaylistsPageable(int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest=PageRequest.of(page, elements, sort);
        return playlistRepository.getPlaylistPageable(pageRequest);
    }

    public Page<Playlist> getPlaylistsByUserPageable(Long idUser,int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest=PageRequest.of(page, elements, sort);
        return playlistRepository.getPlaylistByUser(idUser,pageRequest);
    }

}
