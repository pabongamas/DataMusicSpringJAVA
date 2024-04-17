package com.datamusic.datamusic.persistence.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Cancion;

public interface CancionCrudRepository extends CrudRepository<Cancion,Long> {

     List<Cancion> findByIdAlbum(Long idAlbum);

     List<Cancion> findByAlbumIdGenero(Long idGenero);

     List<Cancion> findByAlbumArtistasArtistaIdArtistaOrderByNombreAsc(Long idArtist);

     List<Cancion> findByPlaylistsPlaylistEntityIdPlaylist(Long idPlaylist);

     Optional<Cancion> findByNombreAndIdAlbum(String nombre,Long idAlbum);

}
