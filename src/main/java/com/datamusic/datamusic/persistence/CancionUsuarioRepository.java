package com.datamusic.datamusic.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.repository.SongUserRepository;
import com.datamusic.datamusic.persistence.crud.CancionCrudRepository;
import com.datamusic.datamusic.persistence.crud.CancionUsuarioCrudRepository;
import com.datamusic.datamusic.persistence.crud.UsuarioCrudRepository;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;
import com.datamusic.datamusic.persistence.entity.CancionesUsuariosPK;
import com.datamusic.datamusic.persistence.entity.Usuario;
import com.datamusic.datamusic.persistence.mapper.SongUserMapMapper;

@Repository
public class CancionUsuarioRepository implements SongUserRepository {
    @Autowired
    private CancionUsuarioCrudRepository cancionUsuarioCrudRepository;

    @Autowired
    private SongUserMapMapper songUserMapMapper;

    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Override
    public List<SongUser> getAll() {
        List<CancionesUsuarios> CancionesUsuarios = (List<CancionesUsuarios>) cancionUsuarioCrudRepository.findAll();
        return songUserMapMapper.toSongUserMap(CancionesUsuarios);
    }

    @Override
    public SongUser likedSong(Long userId, Long songId) {
        Optional<Usuario>usuario=usuarioCrudRepository.findById(userId);
        if(!usuario.isEmpty()){
            //create object primary key 
            CancionesUsuariosPK CancionesUsuariosPK=new CancionesUsuariosPK();
            CancionesUsuariosPK.setIdCancion(songId);
            CancionesUsuariosPK.setIdUsuario(userId);

            //create object cancion 
            Optional<Cancion> cancion=cancionCrudRepository.findById(songId);

            if(cancion.isEmpty()){
               throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"Song Not Found");
            }

            //create object cancionUsuario
            CancionesUsuarios cancionUsuario=new CancionesUsuarios();
            cancionUsuario.setId(CancionesUsuariosPK);
            cancionUsuario.setUsuario(usuario.get());
            cancionUsuario.setCancion(cancion.get());
            cancionUsuario.setFecha_agregada(LocalDateTime.now());

            //save liked song and mapper for return
            CancionesUsuarios cancionesUsuariosSaved= cancionUsuarioCrudRepository.save(cancionUsuario);
            return songUserMapMapper.toSongUserMap(cancionesUsuariosSaved);
        }
        return null;
    }

    @Override
    public boolean dislikeSong(Long userId, Long songId) {
        Optional<Usuario>usuario=usuarioCrudRepository.findById(userId);
        if(!usuario.isEmpty()){
            //create object primary key 
            CancionesUsuariosPK CancionesUsuariosPK=new CancionesUsuariosPK();
            CancionesUsuariosPK.setIdCancion(songId);
            CancionesUsuariosPK.setIdUsuario(userId);

            //create object cancion 
            Optional<Cancion> cancion=cancionCrudRepository.findById(songId);

            if(cancion.isEmpty()){
               throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"Song Not Found");
            }

            CancionesUsuarios CancionUsuario=cancionUsuarioCrudRepository.findByIdIdUsuarioAndIdIdCancion(userId, songId);
            // create object cancionUsuario
            // CancionesUsuarios cancionUsuario=new CancionesUsuarios();
            // cancionUsuario.setId(CancionesUsuariosPK);
            // cancionUsuario.setUsuario(usuario.get());
            // cancionUsuario.setCancion(cancion.get());
            // cancionUsuario.setFecha_agregada(LocalDateTime.now());

            //remove liked song of table
            cancionUsuarioCrudRepository.delete(CancionUsuario);
            return true;
        }
        return false;
    }

}
