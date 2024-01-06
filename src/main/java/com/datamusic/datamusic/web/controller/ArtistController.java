package com.datamusic.datamusic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping("/all")
     public ResponseEntity<List<Artist>> getAll() {
        // return productService.getAll();
        return new ResponseEntity<>(artistService.getAll(), HttpStatus.OK);
    }
}
