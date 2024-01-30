package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.repository.GenderRepository;
import com.datamusic.datamusic.persistence.crud.AlbumCrudRepository;
import com.datamusic.datamusic.persistence.crud.GeneroCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.entity.Genero;
import com.datamusic.datamusic.persistence.mapper.GenderMapper;

@Repository
public class GeneroRepository implements GenderRepository {

    @Autowired
    private GeneroCrudRepository generoCrudRepository;

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    @Autowired
    private GenderMapper mapper;
    

    @Override
    public List<Gender> getAll() {
        List<Genero> generos = (List<Genero>) generoCrudRepository.findAll();
        // generos.forEach(genero->{
        //     genero.getAlbums().forEach(album->{
        //         album.getArtistas().forEach(artist->{
        //             System.out.println(artist.getArtista().getNombre());
        //         });
        //     });
        // });
        return mapper.toGenders(generos);
    }

    @Override
    public Optional<Gender> getGender(Long genderId) {
        return generoCrudRepository.findById(genderId).map(gender -> mapper.toGender(gender));
    }

    @Override
    public Gender save(Gender gender) {
        Genero genero = mapper.toGenero(gender);
        return mapper.toGender(generoCrudRepository.save(genero));
    }

    @Override
    public boolean delete(Long genderId) {
        //verificar si existe en la tabla albums , albums con ese id genero , si lo hay no permitir eliminar 
        // genero por violacion de llave foranea 

        List<AlbumEntity> albumsXgenero=albumCrudRepository.findByIdGenero(genderId);
        if (!albumsXgenero.isEmpty()) {
            Optional<Gender> gender=getGender(genderId);
            String genderName = gender.map(Gender::getName).orElse("");
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "No se puede eliminar el género " + genderName + " porque tiene álbumes asociados");
        }
        generoCrudRepository.deleteById(genderId);
        return true;
    }

    @Override
    public List<Gender> getGenerosByNombre(String nombre) {
        List<Genero> generos = (List<Genero>) generoCrudRepository.findByNombre(nombre);
        return mapper.toGenders(generos);
    }

}
