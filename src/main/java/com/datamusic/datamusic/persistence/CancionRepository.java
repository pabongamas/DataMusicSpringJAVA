package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.repository.SongRepository;
import com.datamusic.datamusic.persistence.crud.CancionCrudRepository;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.mapper.SongMapper;

@Repository
public class CancionRepository implements SongRepository {

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Autowired
    private SongMapper mapper;

    @Override
    public List<Song> getAll() {
        List<Cancion> canciones = (List<Cancion>) cancionCrudRepository.findAll();
        return mapper.toSong(canciones);
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
    public Optional<Song> getSongByNameAndAlbumId(String name,Long idAlbum) {
        return cancionCrudRepository.findByNombreAndIdAlbum(name,idAlbum).map(cancion->mapper.toSong(cancion));
    }

    @Override
    public void delete(Long songId) {
        cancionCrudRepository.deleteById(songId);
    }
}
