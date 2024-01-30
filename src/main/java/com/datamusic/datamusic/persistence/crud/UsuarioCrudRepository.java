package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Usuario;

public interface UsuarioCrudRepository extends CrudRepository<Usuario,Long> {
    
}
