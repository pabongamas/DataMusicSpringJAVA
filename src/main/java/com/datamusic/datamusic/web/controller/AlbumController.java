package com.datamusic.datamusic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.service.AlbumService;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping("/all")
     public ResponseEntity<List<Album>> getAll() {
        // return productService.getAll();
        return new ResponseEntity<>(albumService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Album> getAlbumById(@PathVariable("id")Long albumId){
        return albumService.getAlbumById(albumId).map(album->new ResponseEntity<>(album,HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/save")
    public ResponseEntity<Album> save(@RequestBody Album album) {
        return new ResponseEntity<>(albumService.save(album), HttpStatus.CREATED);
    }
}
