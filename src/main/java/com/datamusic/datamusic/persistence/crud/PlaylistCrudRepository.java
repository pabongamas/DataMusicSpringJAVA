package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.datamusic.datamusic.persistence.entity.PlaylistEntity;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

public interface PlaylistCrudRepository extends CrudRepository<PlaylistEntity, Long> {

    List<PlaylistEntity> findByIdUsuario(Long idUser);

    @Query(value = "select song.*,albums.id_album as id_album,albums.nombre as nombre_album,albums.anio as anio_album from playlists join canciones_playlists using(id_playlist) join canciones song using(id_cancion) "+
    " join albums  using (id_album) where id_playlist=:playlistId ", nativeQuery = true)
    List<PlaylistSongsSummary> findSummary(@Param("playlistId") Long playlistId);

}
