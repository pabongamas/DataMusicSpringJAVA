package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.entity.CancionPlaylistPK;

public interface CancionPlaylistCrudRepository extends CrudRepository<CancionPlaylist,CancionPlaylistPK> {
    
}
