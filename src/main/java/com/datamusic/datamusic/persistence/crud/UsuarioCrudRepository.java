package com.datamusic.datamusic.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.Usuario;

import java.util.Optional;


public interface UsuarioCrudRepository extends CrudRepository<Usuario,Long> {
 
    Optional<Usuario> findByCorreoElectronico(String email);
}
