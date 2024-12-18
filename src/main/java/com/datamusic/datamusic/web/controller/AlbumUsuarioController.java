package com.datamusic.datamusic.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.service.AlbumUserService;
import com.datamusic.datamusic.domain.service.SongUserService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.AlbumUser;
import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.SongUser;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/AlbumUser")
public class AlbumUsuarioController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Album Usuario No Encontrado";

    @Autowired
    private AlbumUserService albumUserService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<AlbumUser> albumUser = albumUserService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albumUser", albumUser);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/pageable")
    public ResponseEntity<ApiResponse> getAllByPageable(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements, @RequestParam(defaultValue = "id_album") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            albumUserService.getAllByPage(page, elements, sortBy, sortDirection);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @GetMapping("/liked")
    public ResponseEntity<ApiResponse> getAlbumsLikedByUser(HttpServletRequest request) {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            List<AlbumUser> albumsLikedsByUser = albumUserService.getAlbumsLikedByUser(token);
            List<Album> albums=new ArrayList<>();
            albumsLikedsByUser.forEach(albumdat->{
                albums.add(albumdat.getAlbum());
            });
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albums);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        }
    }
    
  @PostMapping("/like/{id}")
  public ResponseEntity<ApiResponse> newLikedAlbumUser(@PathVariable("id") Long idAlbum,HttpServletRequest request){
    try {
      String token= request.getHeader(HttpHeaders.AUTHORIZATION);
      AlbumUser  albumUser = albumUserService.likeAlbumUser(idAlbum,token);
      ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
      response.addData("liked",albumUser);
      return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

     @DeleteMapping("/like/{id}")
    public ResponseEntity<ApiResponse> deleteLikedAlbumUser(@PathVariable("id") Long idAlbum,HttpServletRequest request){
      try {
        String token= request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean albumUser = albumUserService.dislikeAlbumUser(idAlbum,token);

        ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
        response.addData("disliked",albumUser);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
      }catch (SQLGrammarException ex) {
              return new ResponseEntity<ApiResponse>(
                      new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                      HttpStatus.INTERNAL_SERVER_ERROR);
          } 
      }
}
