package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.PlaylistEntity;

public interface PlaylistCrudRepository extends CrudRepository<PlaylistEntity,Long> {
    
}
