package com.datamusic.datamusic.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.service.SongService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class CancionController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Album No Encontrado";

    @Autowired
    private SongService songService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAll(){
        try {
            List<Song> songs=songService.getAll();
            ApiResponse response=new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songs", songs);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones", null),
                    HttpStatus.NOT_FOUND);
        }
    
    }

}
