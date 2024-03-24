package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            boolean rta = genderRepository.delete(genderId);
            return rta;
        }).orElse(false);
    }

    public List<Gender> getGendersByName(String name) {
        return genderRepository.getGenerosByNombre(name);
    }

    public Page<Gender> GetAllPageable(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return genderRepository.getAllPageable(pageRequest);

    }
}
