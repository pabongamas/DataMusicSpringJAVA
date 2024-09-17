package com.datamusic.datamusic.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.repository.SongUserRepository;
import com.datamusic.datamusic.persistence.crud.CancionUsuarioCrudRepository;
import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;
import com.datamusic.datamusic.persistence.mapper.SongUserMapMapper;

@Repository
public class CancionUsuarioRepository implements SongUserRepository {
    @Autowired
    private CancionUsuarioCrudRepository cancionUsuarioCrudRepository;

    @Autowired
    private SongUserMapMapper songUserMapMapper;

    @Override
    public List<SongUser> getAll() {
        List<CancionesUsuarios> CancionesUsuarios = (List<CancionesUsuarios>) cancionUsuarioCrudRepository.findAll();
        return songUserMapMapper.toSongUserMap(CancionesUsuarios);
    }

}
