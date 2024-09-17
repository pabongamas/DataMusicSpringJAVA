package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;
import com.datamusic.datamusic.persistence.entity.CancionesUsuariosPK;

public interface CancionUsuarioCrudRepository extends CrudRepository<CancionesUsuarios,CancionesUsuariosPK>{
     List<CancionesUsuarios> findByIdIdCancion(Long idCancion);
     List<CancionesUsuarios> findByIdIdUsuario(Long idUsuario);

}
