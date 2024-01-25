package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Cancion;

public interface CancionCrudRepository extends CrudRepository<Cancion,Long> {
    
}
