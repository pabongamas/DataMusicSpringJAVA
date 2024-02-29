package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.repository.SongPlaylistRepository;
import com.datamusic.datamusic.persistence.crud.CancionCrudRepository;
import com.datamusic.datamusic.persistence.crud.CancionPlaylistCrudRepository;
import com.datamusic.datamusic.persistence.crud.PlaylistCrudRepository;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.entity.PlaylistEntity;
import com.datamusic.datamusic.persistence.mapper.SongPlaylistMapMapper;

@Repository
public class CancionPlaylistRepository implements SongPlaylistRepository {
    @Autowired
    private CancionPlaylistCrudRepository cancionPlaylistCrudRepository;
    @Autowired
    private SongPlaylistMapMapper mapper;

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Autowired
    private PlaylistCrudRepository playlistCrudRepository;

    @Override
    public List<SongPlaylist> getAll() {
        List<CancionPlaylist> cancionPlaylists = (List<CancionPlaylist>) cancionPlaylistCrudRepository.findAll();
        return mapper.toSongPlaylistMap(cancionPlaylists);
    }

    @Override
    public List<SongPlaylist> getSongPlaylistBySongId(Long songId) {
        List<CancionPlaylist> bySongId = (List<CancionPlaylist>) cancionPlaylistCrudRepository
                .findByIdIdCancion(songId);
        return mapper.toSongPlaylistMap(bySongId);

    }

    @Override
    public List<SongPlaylist> getSongPlaylistByPlaylistId(Long playlistId) {
        List<CancionPlaylist> byPlaylistId = (List<CancionPlaylist>) cancionPlaylistCrudRepository
                .findByIdIdPlaylist(playlistId);
        return mapper.toSongPlaylistMap(byPlaylistId);
    }

    @Override
    public SongPlaylist save(SongPlaylist SongPlaylist) {
        // AlbumsArtista albumArtista = mapperMap.toAlbumsArtistaMap(albumArtist);
        CancionPlaylist cancionPlaylist = mapper.toCancionPlaylist(SongPlaylist);

        if (cancionPlaylist.getCancion() == null) {
            Optional<Cancion> cancion = cancionCrudRepository.findById(SongPlaylist.getSongId());
            cancion.ifPresent(cancionEntity -> cancionPlaylist.setCancion(cancionEntity));
        }
        if (cancionPlaylist.getPlaylistEntity() == null) {
            Optional<PlaylistEntity> playlist = playlistCrudRepository.findById(SongPlaylist.getIdPlaylist());
            playlist.ifPresent(playlistEntity->cancionPlaylist.setPlaylistEntity(playlistEntity));
        }

        CancionPlaylist cancionPlaylistSaved=cancionPlaylistCrudRepository.save(cancionPlaylist);

        return mapper.toSongPlaylistMap(cancionPlaylistSaved);
    }

}
