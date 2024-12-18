package com.datamusic.datamusic.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.service.SongService;
import com.datamusic.datamusic.domain.service.SongUserService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/songUser")
public class CancionUsuarioController {
  private static final String ERROR_MESSAGE = "Han ocurrido errores";
  private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
  private static final String NOT_FOUND_MESSAGE = "Cancion Usuario No Encontrado";

  @Autowired
  private SongUserService songUserService;

  @Autowired
  private SongService songService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAll() {
    try {
      List<SongUser> songUser = songUserService.getAll();
      ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
      response.addData("songUser", songUser);
      return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    } catch (Exception e) {
      Map<String, String> errors = new HashMap<String, String>();
      errors.put("error", e.getMessage());
      return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<ApiResponse> getSongsLikedByUser(@PathVariable("id") Long idUser,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int elements,
      @RequestParam(defaultValue = "numeroCancion") String sortBy,
      @RequestParam(defaultValue = "ASC") String sortDirection) {
    try {

      Page<Song> songsLikedByUser = songService.getSongsLikedByUser(idUser, page, elements, sortBy, sortDirection);
      ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
      response.addData("songs", songsLikedByUser.getContent());
      response.addData("pageable", songsLikedByUser.getPageable());
      response.addData("totalElements", songsLikedByUser.getTotalElements());
      response.addData("elementsByPage", songsLikedByUser.getSize());
      response.addData("totalPages", songsLikedByUser.getTotalPages());
      return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    } catch (Exception e) {
      Map<String, String> errors = new HashMap<String, String>();
      errors.put("error", e.getMessage());
      return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.NOT_FOUND);
    }
  }
  //songs liked of an album  by user 
  @GetMapping("/album/{id}/songs/liked")
  public ResponseEntity<ApiResponse> getSongsLikedOfAlbumByUser(@PathVariable("id") Long idAlbum,HttpServletRequest request){
    try {
      String token= request.getHeader(HttpHeaders.AUTHORIZATION);
      List<Song> songsLikedOfAlbumByUser=songService.getSongsLikedOfAlbumByUser(token, idAlbum);
      ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
      response.addData("songs",songsLikedOfAlbumByUser);
      return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    } catch (Exception e) {
      Map<String, String> errors = new HashMap<String, String>();
      errors.put("error", e.getMessage());
      return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/like/{id}")
  public ResponseEntity<ApiResponse> newLikedSongUser(@PathVariable("id") Long idSong,HttpServletRequest request){
    try {
      String token= request.getHeader(HttpHeaders.AUTHORIZATION);
      SongUser SongUser = songUserService.likeSong(idSong,token);
      ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
      response.addData("liked",SongUser);
      return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
    @DeleteMapping("/like/{id}")
    public ResponseEntity<ApiResponse> deleteLikedSongUser(@PathVariable("id") Long idSong,HttpServletRequest request){
      try {
        String token= request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean SongUser = songUserService.dislikeSongUser(idSong,token);

        ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
        response.addData("disliked",SongUser);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
      }catch (SQLGrammarException ex) {
              return new ResponseEntity<ApiResponse>(
                      new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                      HttpStatus.INTERNAL_SERVER_ERROR);
          } 
      }
  
  }


