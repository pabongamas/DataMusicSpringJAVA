package com.datamusic.datamusic.persistence;

import java.time.LocalDateTime;
// import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.projection.SummaryPlaylistSong;
import com.datamusic.datamusic.domain.repository.PlaylistRepository;
import com.datamusic.datamusic.persistence.crud.PlaylistCrudRepository;
import com.datamusic.datamusic.persistence.entity.PlaylistEntity;
import com.datamusic.datamusic.persistence.mapper.PlaylistMapper;
import com.datamusic.datamusic.persistence.mapper.mappersSummary.PlaylistSongsSummaryMapper;

import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;


@Repository
public class PlaylistEntityRepository implements PlaylistRepository{

    @Autowired
    private PlaylistCrudRepository playlistCrudRepository;

    @Autowired
    private PlaylistMapper mapper;

    @Autowired
    private PlaylistSongsSummaryMapper mapperSummaryPlaylistSongs;

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

    @Override
    public List<Playlist> getPlaylistByUser(Long idUser) {
       List<PlaylistEntity> playlistsByUser= (List<PlaylistEntity>) playlistCrudRepository.findByIdUsuario(idUser);
       return mapper.toPlaylists(playlistsByUser);
    }

    @Override
    public List<SummaryPlaylistSong> getSongs(Long idPlaylist) {
        List<PlaylistSongsSummary> songs=(List<PlaylistSongsSummary>) playlistCrudRepository.findSummary(idPlaylist);
        //  Iterator<PlaylistSongsSummary>iterator=songs.iterator();
        //     while (iterator.hasNext()) {
        //         PlaylistSongsSummary dataPlaylist = iterator.next();
        //         System.out.println(dataPlaylist.getNombre_Album());
        //     }
        return mapperSummaryPlaylistSongs.toSummaryPlaylistsSong(songs);
    }
    
}
