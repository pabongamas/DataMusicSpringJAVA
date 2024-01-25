package com.datamusic.datamusic.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.repository.SongRepository;
import com.datamusic.datamusic.persistence.crud.CancionCrudRepository;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.mapper.SongMapper;

@Repository
public class CancionRepository implements SongRepository {

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Autowired
    private SongMapper mapper;

    @Override
    public List<Song> getAll() {
        List<Cancion> canciones = (List<Cancion>) cancionCrudRepository.findAll();
        return mapper.toSong(canciones);
    }

}
