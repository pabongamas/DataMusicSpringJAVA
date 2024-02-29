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

    @Query(value = "select song.*,albums.id_album as id_album,albums.nombre as nombre_album,albums.anio as anio_album,albums.id_genero from playlists join canciones_playlists using(id_playlist) join canciones song using(id_cancion) "
            +
            " join albums  using (id_album) where id_playlist=:playlistId ", nativeQuery = true)
    Page<PlaylistSongsSummary> findPlaylistSongSummary(@Param("playlistId") Long playlistId, Pageable pageable);
}
