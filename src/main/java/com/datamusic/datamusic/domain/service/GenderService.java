package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.repository.GenderRepository;

@Service
public class GenderService {
    @Autowired
    private GenderRepository genderRepository;

    public List<Gender> getAll() {
        return genderRepository.getAll();
    }

    public Optional<Gender> getGender(Long genderId) {
        return genderRepository.getGender(genderId);
    }

    public Gender save(Gender gender) {
        return genderRepository.save(gender);
    }

    public boolean delete(Long genderId) {
        return getGender(genderId).map(gender -> {
            genderRepository.delete(genderId);
            return true;
        }).orElse(false);
    }
}
