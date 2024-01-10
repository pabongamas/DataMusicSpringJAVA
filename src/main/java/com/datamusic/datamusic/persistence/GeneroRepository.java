package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.repository.GenderRepository;
import com.datamusic.datamusic.persistence.crud.GeneroCrudRepository;
import com.datamusic.datamusic.persistence.entity.Genero;
import com.datamusic.datamusic.persistence.mapper.GenderMapper;

@Repository
public class GeneroRepository implements GenderRepository {

    @Autowired
    private GeneroCrudRepository generoCrudRepository;

    @Autowired
    private GenderMapper mapper;

    @Override
    public List<Gender> getAll() {
        List<Genero> generos = (List<Genero>) generoCrudRepository.findAll();
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

}
