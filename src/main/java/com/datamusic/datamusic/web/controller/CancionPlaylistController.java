package com.datamusic.datamusic.web.controller;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.service.SongPlaylistService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/songPlaylist")
public class CancionPlaylistController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Cancion Playlist No Encontrado";

    @Autowired
    private SongPlaylistService songPlaylistService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<SongPlaylist> songPlaylists = songPlaylistService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songPlaylist", songPlaylists);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false,
                            "No se ha Recuperado la informac&oacute; de las canciones Playlists, se han presentado errores",
                            null, errors),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<ApiResponse> getSongPlaylistBySongId(@PathVariable("id") Long songId) {
        try {
            List<SongPlaylist> songPlaylistBySongId = songPlaylistService.getSongPlaylistBySongId(songId);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songPlaylist", songPlaylistBySongId);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,
                    "No se ha recuperado la informacion de las canciones Playlist por la busqueda de esta cancion",
                    null, errors), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<ApiResponse> getSongPlaylistByPlaylistId(@PathVariable("id") Long playlistId) {
        try {
            List<SongPlaylist> songPlaylistByPlaylistId = songPlaylistService.getSongPlaylistByPlaylistId(playlistId);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songPlaylist", songPlaylistByPlaylistId);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,
                    "No se ha recuperado la informacion de las canciones Playlist por la busqueda de esta cancion",
                    null, errors), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveSongPlaylist(@RequestBody SongPlaylist songPlaylist){
        try {
            SongPlaylist songSaved=songPlaylistService.saveSongPlaylist(songPlaylist);
            ApiResponse response=new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songPlaylist", songSaved);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        } catch (SQLGrammarException ex) {
           return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
