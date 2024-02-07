package com.datamusic.datamusic.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.service.PlaylistService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/playlists")
public class PlaylistsController {

    @Autowired
    private PlaylistService playlistService;
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Playlist No Encontrada";

    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAll(){
        try {
            List<Playlist>playlists=playlistService.getAll();
            ApiResponse response=new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("playlists",playlists);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"No se ha Recuperado la informac&oacute; de Usuarios"+e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse>getPlaylistById(@PathVariable("id") Long playlistId){
        Optional<Playlist>playlistById= playlistService.getPlaylistById(playlistId);

        if (playlistById.isPresent()) {
            Playlist playlist=playlistById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("user", playlist);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }else{
            Map<String,String>errors=new HashMap<String,String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
            HttpStatus.NOT_FOUND);

        }
    }
    
}
