package com.datamusic.datamusic.persistence.crud;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Genero;

public interface GeneroCrudRepository extends CrudRepository<Genero,Long> {
      List<Genero> findByNombre(String nombre);
}
