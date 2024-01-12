package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.Gender;
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

    @Override
    public Optional<Artist> getArtist(Long artistId) {
      return artistaCrudRepository.findById(artistId).map(artist->mapper.toArtist(artist));

    }

    @Override
    public Artist save(Artist artist) {
      // verifico si existen artistas ya con ese nombre
      List<Artist> artistasByName=getArtistsByName(artist.getName());
      if (!artistasByName.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "El artista  " + artist.getName() + " ya se encuentra registrado.");
        }
     
      Artista artista=mapper.toArtista(artist);
      return mapper.toArtist(artistaCrudRepository.save(artista));
    }

    @Override
    public void delete(Long artistId) {
      artistaCrudRepository.deleteById(artistId);
    }

    @Override
    public List<Artist> getArtistsByName(String name) {
        List<Artista> artistasByName = (List<Artista>)artistaCrudRepository.findByNombre(name);
        return mapper.toArtists(artistasByName);

    }
    
}
