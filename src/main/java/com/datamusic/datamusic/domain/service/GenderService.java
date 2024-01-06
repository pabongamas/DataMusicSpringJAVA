package com.datamusic.datamusic.domain.service;

import java.util.List;

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
}
