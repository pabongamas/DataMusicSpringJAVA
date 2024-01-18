package com.datamusic.datamusic.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.service.ArtistService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Artista No Encontrado";

    @Autowired
    private ArtistService artistService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Artist> artists = artistService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("artists", artists);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Artistas"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getArtistById(@PathVariable("id") Long artistId) {
        Optional<Artist> artists = artistService.getArtistById(artistId);

        if (artists.isPresent()) {
            Artist artist = artists.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("artist", artist);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error",NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Artist artist) {
        try {
            List<Artist> artistsByName = artistService.geArtistsByName(artist.getName());

            if (!artistsByName.isEmpty()) {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", "El nombre de artista " + artist.getName() + " ya se encuentra registrado");
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.BAD_REQUEST);
            }
            Artist artistSaved = artistService.save(artist);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("artist", artistSaved);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long artistId) {
        try {
            boolean artistDelete=artistService.delete(artistId);
            if(artistDelete){
              return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESSFUL_MESSAGE,null),HttpStatus.OK);
            }
            Map<String,String>errors=new HashMap<String,String>();
            errors.put("error",NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE,null,errors),HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, ERROR_MESSAGE, null, errors),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
