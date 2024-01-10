package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.Gender;

public interface GenderRepository {
    List<Gender>getAll();
    Optional<Gender>getGender(Long genderId);
    Gender save(Gender gender);
}
