package com.datamusic.datamusic.web.controller;

import org.apache.catalina.connector.Response;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.service.SongService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class CancionController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Canción No Encontrada";

    @Autowired
    private SongService songService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Song> songs = songService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songs", songs);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones", null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBysongId(@PathVariable("id") Long songId) {
        Optional<Song> songById = songService.getSong(songId);

        if (songById.isPresent()) {
            Song song = songById.get();
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("song", song);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        }
        Map<String,String> errors=new HashMap<String,String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.NOT_FOUND);
    }
    @GetMapping("/album/{id}")
    public ResponseEntity<ApiResponse> getSongsByAlbumId(@PathVariable("id") Long albumId){
        try {
            List<Song> songsByAlbumId = songService.getSongsByAlbumId(albumId);
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("songsByAlbum",songsByAlbumId);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este album", null),
                    HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/gender/{id}")
    public ResponseEntity<ApiResponse> getSongsByGenderId(@PathVariable("id") Long genderId){
        try {
            List<Song> songsByGenderId = songService.getSongsByGeneroId(genderId);
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("songsByGender",songsByGenderId);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Genero", null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<ApiResponse> getSongsByArtist(@PathVariable("id") Long artistId){
        try {
            List<Song>songsByArtistId=songService.getSongsByArtistId(artistId);
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("songsByArtist",songsByArtistId);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Artista", null),
                HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Song song){
        try {
            Optional<Song> validCancion=songService.getSongByName(song.getName(),song.getAlbumId());
            if(validCancion.isPresent()){
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", "La cancion "+song.getName()+" ya esta incluida en este album "+song.getAlbum().getAlbumId());
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.BAD_REQUEST);
            }

            Song songSaved=songService.save(song);
            ApiResponse response=new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("song", songSaved);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }  catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "El album con id "+song.getAlbumId()+" no se encuentra registrado",
                            null),
                    HttpStatus.CONFLICT);
        }
    }



}
