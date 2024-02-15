package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.entity.CancionPlaylistPK;

public interface CancionPlaylistCrudRepository extends CrudRepository<CancionPlaylist,CancionPlaylistPK> {

    List<CancionPlaylist> findByIdIdCancion(Long idCancion);

    List<CancionPlaylist> findByIdIdPlaylist(Long idPlaylist);
}
