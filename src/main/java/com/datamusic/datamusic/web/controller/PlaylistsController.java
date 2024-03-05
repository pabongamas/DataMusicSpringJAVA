package com.datamusic.datamusic.web.controller;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.domain.User;
import com.datamusic.datamusic.domain.projection.SummaryPlaylistSong;
import com.datamusic.datamusic.domain.service.PlaylistService;
import com.datamusic.datamusic.domain.service.UserService;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/playlists")
public class PlaylistsController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired 
    UserService userService;

    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Playlist No Encontrada";

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        try {
            List<Playlist>playlists=playlistService.getAll();
            ApiResponse response=new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("playlists",playlists);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"No se ha Recuperado la informac&oacute; de Usuarios"+e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("user/{id}")
    public ResponseEntity<ApiResponse> getPlaylistByUser(@PathVariable("id") Long idUser){
        try {
            List<Playlist> playlistsByUser=playlistService.getPlaylistsByUser(idUser);
            // playlistsByUser.forEach(playlist->{
            //     playlist.setUser(null);
            // });
            //iterator maneja colecciones de datos permitiendo validar si hay  registro siguiente para seguir recorriendo y con next para obtener el
            // siguiente valor , aca seteo la informacion de user null , porque como todas estas playlist pertenecen al mismo usuario seria 
            // muy redundante mostrar siempre el mismo nombre del usuario
            
            Iterator<Playlist>iterator=playlistsByUser.iterator();
            while (iterator.hasNext()) {
                Playlist dataPlaylist = iterator.next();
                dataPlaylist.setUser(null);
            }
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("playlists", playlistsByUser);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"No se ha Recuperado la informac&oacute; de las Playlist "+e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPlaylistById(@PathVariable("id") Long playlistId){
        Optional<Playlist>playlistById= playlistService.getPlaylistById(playlistId);

        if (playlistById.isPresent()) {
            Playlist playlist=playlistById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("playlist", playlist);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }else{
            Map<String,String>errors=new HashMap<String,String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
            HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> savePlaylist(@Valid @RequestBody Playlist playlist){
        try {
            Optional<User> userExists=userService.getUserById(playlist.getIdUser());
            if(!userExists.isPresent()){
                Map<String,String> errors=new HashMap<String,String>();
                errors.put("error","el Usuario a vincular en la Playlist no fue encontrado");
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),HttpStatus.NOT_FOUND);
            }
            Playlist playlistCreated=playlistService.save(playlist);
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("playlist", playlistCreated);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
          return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Error de gramática SQL:" + ex.getSQLException()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePlaylist(@PathVariable("id") Long playlistId){
        try {
            boolean playlistDeleted=playlistService.delete(playlistId);
            if(playlistDeleted){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESSFUL_MESSAGE,null),HttpStatus.OK);
            }else{
                Map<String,String> errors=new HashMap<String,String>();
                errors.put("error", NOT_FOUND_MESSAGE);
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.NOT_FOUND);
            }
        } catch (SQLGrammarException e) {
            Map<String,String> errors=new HashMap<String,String>();
            errors.put("error",e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null,errors),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("{id}/songs")
    public ResponseEntity<ApiResponse> getSongsSummary(@PathVariable("id") Long idPlaylist){
        try {
            List<SummaryPlaylistSong> songs=playlistService.getSongs(idPlaylist);
          
         
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("songs", songs);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"No se ha Recuperado la informac&oacute; de las Playlist "+e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("{id}/songsPage")
    public ResponseEntity<ApiResponse> getSongsSummaryPageable(@PathVariable("id") Long idPlaylist,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "1") int elements, @RequestParam(defaultValue = "song.nombre") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDirection){
        try {
            Page<PlaylistSongsSummary> songs=playlistService.getSongsPageable(idPlaylist,page,elements,sortBy,sortDirection);
            ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("songs", songs.getContent());
            response.addData("pageable", songs.getPageable());
            response.addData("totalElements", songs.getTotalElements());
            response.addData("elementsByPage", songs.getSize());
            response.addData("totalPages", songs.getTotalPages());
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"No se ha Recuperado la informac&oacute; de las Playlist "+e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    
    // @GetMapping("/available")
    // public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "8") int elements, @RequestParam(defaultValue = "price") String sortBy,
    //         @RequestParam(defaultValue = "ASC") String sortDirection) {
    //     Page<PizzaEntity> pizzas = this.pizzaService.getAvailable(page, elements, sortBy,sortDirection);
    //     return ResponseEntity.ok(pizzas);
    // }
}
