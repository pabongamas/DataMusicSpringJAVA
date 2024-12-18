package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.AlbumsUsuario;
import com.datamusic.datamusic.persistence.entity.AlbumsUsuarioPK;
import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;

public interface AlbumUsuarioCrudRepository extends CrudRepository<AlbumsUsuario,AlbumsUsuarioPK>{
    
   List<AlbumsUsuario> findByIdIdUsuario(Long idUser);
   AlbumsUsuario findByIdIdUsuarioAndIdIdAlbum(Long idUsuario,Long idAlbum);
}
