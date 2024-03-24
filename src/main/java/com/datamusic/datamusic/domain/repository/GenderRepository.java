package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.Gender;

public interface GenderRepository {
    List<Gender>getAll();
    Optional<Gender>getGender(Long genderId);
    Gender save(Gender gender);
    boolean delete(Long genderId);
    List<Gender>getGenerosByNombre(String nombre);
    Page<Gender>getAllPageable(Pageable pageable);
}
