package com.datamusic.datamusic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.service.GenderService;

@RestController
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping("/all")
     public ResponseEntity<List<Gender>> getAll() {
        // return productService.getAll();
        return new ResponseEntity<>(genderService.getAll(), HttpStatus.OK);
    }
    
}
