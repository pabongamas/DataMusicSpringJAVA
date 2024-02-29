package com.datamusic.datamusic.web.controller;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.User;
import com.datamusic.datamusic.domain.service.PlaylistService;
import com.datamusic.datamusic.domain.service.UserService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Usuario No Encontrado";
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<User> users = userService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("users", users);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de Usuarios" + e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") long idUser) {
        Optional<User> userById = userService.getUserById(idUser);

        if (userById.isPresent()) {
            User user = userById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("user", user);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } else {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody User user) {
        try {
            User userCreated = userService.saveUser(user);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("user", userCreated);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> save(@PathVariable("id") Long idUser, @RequestBody User updatedUser) {
        try {
            Optional<User> userById = userService.getUserById(idUser);
            if (userById.isPresent()) {
                User userExisting = userById.get();
                if (updatedUser.getName() != null) {
                    userExisting.setName(updatedUser.getName());
                }
                if (updatedUser.getLastnames() != null) {
                    userExisting.setLastnames(updatedUser.getLastnames());
                }
                if (updatedUser.getEmail() != null) {
                    userExisting.setEmail(updatedUser.getEmail());
                }
                if (updatedUser.getPassword() != null) {
                    userExisting.setPassword(updatedUser.getPassword());
                }
                User userUpdated = userService.saveUser(userExisting);
                ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
                response.addData("user", userUpdated);
                return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
            } else {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", NOT_FOUND_MESSAGE);
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.NOT_FOUND);
            }

        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long idUser) {
        try {
            List<Playlist> playlistsByUser = playlistService.getPlaylistsByUser(idUser);
            if (!playlistsByUser.isEmpty()) {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", "El usuario esta siendo utlizado , NO puede ser eliminado");
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.CONFLICT);
            }
            boolean userDeleted = userService.delete(idUser);
            if (userDeleted) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(userDeleted, SUCCESSFUL_MESSAGE, null),
                        HttpStatus.OK);
            }
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        } catch (SQLGrammarException e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.CONFLICT);
        }
    }

}
