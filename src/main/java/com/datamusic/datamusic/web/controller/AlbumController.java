package com.datamusic.datamusic.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.service.AlbumService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Album No Encontrado";
    @Autowired
    private AlbumService albumService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Album> albums = albumService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albums);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums", null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAlbumById(@PathVariable("id") Long albumId) {
        Optional<Album> albumById = albumService.getAlbumById(albumId);
        if (albumById.isPresent()) {
            Album album = albumById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("album", album);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/gender/{id}")
    public ResponseEntity<ApiResponse> getAlbumByGenderId(@PathVariable("id") Long genderId) {

        try {
            List<Album> albumsByGender = albumService.getAlbumByGenderId(genderId);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albumsByGender);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Por el genero", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Album album) {
        try {
            Album albumsaved = albumService.save(album);

            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("album", albumsaved);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(DataIntegrityViolationException ex){
            return new ResponseEntity<ApiResponse>(
                new ApiResponse(false, "Error de gramática SQLsasa:" + ex.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long albumId) {
        if (albumService.delete(albumId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
