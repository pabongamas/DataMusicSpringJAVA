package com.datamusic.datamusic.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.repository.ArtistRepository;
import com.datamusic.datamusic.persistence.crud.ArtistaCrudRepository;
import com.datamusic.datamusic.persistence.entity.Artista;
import com.datamusic.datamusic.persistence.mapper.ArtistMapper;


@Repository
public class ArtistaRepository implements ArtistRepository {

      // se hace el llamado de la inteface para poder utilizar los metodos del crud
    // que nos da crud Repository
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;

    @Autowired
    private ArtistMapper mapper;

    @Override
    public List<Artist> getAll() {
           /*
         * se castea ya que esto recive es un dato de tipo iterable ,entoncs se castea a
         * una lista de producto
         */
        List<Artista> artistas = (List<Artista>) artistaCrudRepository.findAll();
        return mapper.toArtists(artistas);
    }
    
}
