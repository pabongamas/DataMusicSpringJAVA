package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.PlaylistEntity;

public interface PlaylistCrudRepository extends CrudRepository<PlaylistEntity,Long> {

         List<PlaylistEntity> findByIdUsuario(Long idUser);
    
}
