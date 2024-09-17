package com.datamusic.datamusic.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.service.SongUserService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

@RestController
@RequestMapping("/songUser")
public class CancionUsuarioController {
  private static final String ERROR_MESSAGE = "Han ocurrido errores";
  private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
  private static final String NOT_FOUND_MESSAGE = "Cancion Playlist No Encontrado";

  @Autowired
  private SongUserService songUserService;

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
      return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),HttpStatus.NOT_FOUND);
    }
  }
}
