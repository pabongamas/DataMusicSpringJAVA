package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Artista;

public interface ArtistaCrudRepository extends CrudRepository<Artista,Long>{
    
}
