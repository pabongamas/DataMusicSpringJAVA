package com.datamusic.datamusic.persistence.pageableAndSort;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.datamusic.datamusic.persistence.entity.PlaylistEntity;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

public interface PlaylistPagSortRepository extends ListPagingAndSortingRepository<PlaylistEntity, Long> {

    @Query(value = "select song.*,album.nombre as nombre_album,album.anio as anio_album,album.id_genero from playlists join canciones_playlists using(id_playlist) join canciones song using(id_cancion) "
            +
            " join albums as album  using (id_album) where id_playlist=:playlistId ", nativeQuery = true)
    Page<PlaylistSongsSummary> findPlaylistSongSummary(@Param("playlistId") Long playlistId, Pageable pageable);

    Page<PlaylistEntity> findByIdUsuario(Long idUser, Pageable pageable);
}
