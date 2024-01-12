package com.datamusic.datamusic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.service.AlbumArtistService;

@RestController
@RequestMapping("/albumsArtist")
public class AlbumArtistController {

    @Autowired
    private AlbumArtistService albumArtistService;

    @GetMapping("/all")
    public ResponseEntity<List<AlbumArtist>> getAll() {
        // return productService.getAll();
        return new ResponseEntity<>(albumArtistService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<List<AlbumArtist>> getAlbumsArtistByAlbumID(@PathVariable("id") Long idAlbum) {
        return new ResponseEntity<>(albumArtistService.getAlbumArtistByAlbumId(idAlbum), HttpStatus.OK);
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<List<AlbumArtist>> getAlbumsArtistByArtistID(@PathVariable("id") Long idArtist) {
        return new ResponseEntity<>(albumArtistService.getAlbumArtistByArtistId(idArtist), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<AlbumArtist> saveAlbumArtis(@RequestBody AlbumArtist albumArtist){
        return new ResponseEntity<>(albumArtistService.saveAlbumArtist(albumArtist),HttpStatus.CREATED);
        //   return new ResponseEntity<>(albumService.save(album), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{idAlbum}/{idArtista}")
    public ResponseEntity delete(@PathVariable("idAlbum") Long idAlbum,@PathVariable("idArtista") Long idArtista){
        if(albumArtistService.delete(idAlbum,idArtista)){

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
