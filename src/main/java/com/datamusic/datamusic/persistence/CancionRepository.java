package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.repository.SongRepository;
import com.datamusic.datamusic.persistence.crud.CancionCrudRepository;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.mapper.SongMapper;
import com.datamusic.datamusic.persistence.pageableAndSort.CancionesPagSortRepository;

@Repository
public class CancionRepository implements SongRepository {

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Autowired
    private SongMapper mapper;

    @Autowired
    private CancionesPagSortRepository cancionesPagSortRepository;

    @Override
    public List<Song> getAll() {
        List<Cancion> canciones = (List<Cancion>) cancionCrudRepository.findAll();
        return mapper.toSong(canciones);
    }

    @Override
    public Page<Song> getAllPageable(Pageable pageable) {
        Page<Cancion> cancionesByPageable = cancionesPagSortRepository.findAll(pageable);
        List<Cancion>cancionesList=cancionesByPageable.getContent();
        List<Song> songsByPageable=mapper.toSong(cancionesList);
        return new PageImpl<Song>(songsByPageable, pageable,cancionesByPageable.getTotalElements());
    }

    @Override
    public Optional<Song> getSong(Long songId) {
        return cancionCrudRepository.findById(songId).map(cancion -> mapper.toSong(cancion));
    }

    @Override
    public List<Song> getSongsByAlbumId(Long albumId) {
        List<Cancion> canciones = (List<Cancion>) cancionCrudRepository.findByIdAlbum(albumId);
        return mapper.toSong(canciones);
    }
    @Override
    public List<Song> getSongsByPlaylistId(Long playlistId) {
        List<Cancion> canciones = cancionCrudRepository.findByPlaylistsPlaylistEntityIdPlaylist(playlistId);
        return mapper.toSong(canciones);
    }
    @Override
    public Page<Song> getSongsByPlaylistIdPage(Long playlistId,Pageable pageable) {
        Page<Cancion> getSongsByPlaylistIdPage= cancionesPagSortRepository.findByPlaylistsPlaylistEntityIdPlaylist(playlistId,pageable);
        List<Cancion> getSongsByPlaylistIdList=getSongsByPlaylistIdPage.getContent();

       List<Song> songsByPlaylistId=mapper.toSong(getSongsByPlaylistIdList);
       return new PageImpl<Song>(songsByPlaylistId, pageable,getSongsByPlaylistIdPage.getTotalElements());
    }


    @Override
    public List<Song> getSongsByGeneroId(Long generoId) {
        List<Cancion> canciones = (List<Cancion>) cancionCrudRepository.findByAlbumIdGenero(generoId);
        return mapper.toSong(canciones);
    }

    @Override
    public List<Song> getSongsByArtistId(Long artistId) {
        List<Cancion> cancionesArtista = (List<Cancion>) cancionCrudRepository
                .findByAlbumArtistasArtistaIdArtistaOrderByNombreAsc(artistId);
        return mapper.toSong(cancionesArtista);
    }

    @Override
    public Song save(Song song) {
        Cancion cancion = mapper.toCancion(song);
        Cancion cancionSaved = cancionCrudRepository.save(cancion);
        return mapper.toSong(cancionSaved);
    }

    @Override
    public Optional<Song> getSongByNameAndAlbumId(String name, Long idAlbum) {
        return cancionCrudRepository.findByNombreAndIdAlbum(name, idAlbum).map(cancion -> mapper.toSong(cancion));
    }

    @Override
    public void delete(Long songId) {
        cancionCrudRepository.deleteById(songId);
    }

    @Override
    public Page<Song> getSongsLikedByUser(Long userId, Pageable pageable) {
        Page<Cancion>cancionesByUsuario=cancionesPagSortRepository.findByUsuariosUsuarioIdUsuario(userId, pageable);
        List<Cancion> cancionesList=cancionesByUsuario.getContent();
        List<Song> songByUserMapper=mapper.toSong(cancionesList);
        return new PageImpl<Song>(songByUserMapper,pageable,cancionesByUsuario.getTotalElements());
    }

    @Override
    public List<Song> getSongsOfAlbumLikedByUser(Long userId, Long albumId) {
        List<Cancion> SongsLikedOfAlbumByUser=cancionCrudRepository.findByUsuariosIdIdUsuarioAndAlbumIdAlbum(userId,albumId);
        List<Song> SongsLikedOfAlbumByUserMap=mapper.toSong(SongsLikedOfAlbumByUser);
        return SongsLikedOfAlbumByUserMap;
    }

    @Override
    public boolean songIsLiked(Long songId, Long userId, Long albumId) {
        List<Cancion> SongsLikedOfAlbumByUserAndSongID= cancionCrudRepository.findByUsuariosIdIdUsuarioAndAlbumIdAlbumAndIdCancion(userId, albumId,songId);
        List<Song> SongsLikedOfAlbumByUserAndSongIDMap=mapper.toSong(SongsLikedOfAlbumByUserAndSongID);
        if (SongsLikedOfAlbumByUserAndSongIDMap.size()>0) {
            return true;
        }
        return false;
    }

    
}

  
