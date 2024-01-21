package com.datamusic.datamusic.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
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
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/albumsArtist")
public class AlbumArtistController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Album Artista No Encontrado";
    @Autowired
    private AlbumArtistService albumArtistService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {

        try {
            List<AlbumArtist> albumArtistData = albumArtistService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albumsArtist", albumArtistData);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Artistas", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<ApiResponse> getAlbumsArtistByAlbumID(@PathVariable("id") Long idAlbum) {
        try {
            List<AlbumArtist> albumArtistByAlbumId = albumArtistService.getAlbumArtistByAlbumId(idAlbum);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albumsArtist", albumArtistByAlbumId);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Artista", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<ApiResponse> getAlbumsArtistByArtistID(@PathVariable("id") Long idArtist) {
        try {
            List<AlbumArtist> albumsArtistByArtistId = albumArtistService.getAlbumArtistByArtistId(idArtist);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albumsArtist", albumsArtistByArtistId);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Artista", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveAlbumArtis(@Valid @RequestBody AlbumArtist albumArtist) {
        try {
            AlbumArtist albumArtistaSaved = albumArtistService.saveAlbumArtist(albumArtist);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albumsArtist", albumArtistaSaved);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{idAlbum}/{idArtista}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("idAlbum") Long idAlbum,
            @PathVariable("idArtista") Long idArtista) {
        try {
            boolean albumArtistDeleted = albumArtistService.delete(idAlbum, idArtista);
            if (!albumArtistDeleted) {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", NOT_FOUND_MESSAGE);
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESSFUL_MESSAGE, null, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            // retornamos errores por si hubo algun error en la eliminacion del album
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
