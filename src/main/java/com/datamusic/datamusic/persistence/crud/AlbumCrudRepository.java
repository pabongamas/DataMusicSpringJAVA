package com.datamusic.datamusic.persistence.crud;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.AlbumEntity;

public interface AlbumCrudRepository extends CrudRepository<AlbumEntity,Long> {
     List<AlbumEntity> findByIdGenero(Long genderId);
}
