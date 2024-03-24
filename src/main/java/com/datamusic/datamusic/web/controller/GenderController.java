package com.datamusic.datamusic.web.controller;

import java.util.List;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.service.AlbumService;
import com.datamusic.datamusic.domain.service.GenderService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Genero No Encontrado";

    @Autowired
    private GenderService genderService;

    @Autowired
    private AlbumService albumService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Gender> genders = genderService.getAll();
            ApiResponse response = new ApiResponse(true,SUCCESSFUL_MESSAGE);
            response.addData("genders", genders);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "No se ha Recuperado la informac&oacute; de generos"));
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getGenderById(@PathVariable("id") Long genderId) {
        Optional<Gender> genderOptional = genderService.getGender(genderId);

        if (genderOptional.isPresent()) {
            Gender gender = genderOptional.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("gender", gender);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } else {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,ERROR_MESSAGE, null, errors),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Gender gender) {
        try {
            List<Gender> genders = genderService.getGendersByName(gender.getName());
            if (!genders.isEmpty()) {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error", "El Nombre de genero " + gender.getName() + " ya se encuentra registrado");
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors), HttpStatus.BAD_REQUEST);
            }
            Gender genderCreated = genderService.save(gender);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("gender", genderCreated);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Error de gramática SQL:" + ex.getSQLException()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long genderId) {
        try {

            List<Album> albumsByGender=albumService.getAlbumByGenderId(genderId);
            if(!albumsByGender.isEmpty()){
                throw new IllegalStateException("No se puede eliminar el género porque hay álbumes asociados.");
            }
            boolean action = genderService.delete(genderId);
            if (!action) {
                Map<String, String> errors = new HashMap<String, String>();
                errors.put("error",NOT_FOUND_MESSAGE);
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Se ha eliminado correctamente", null), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, ERROR_MESSAGE, null, errors),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/pageable")
    public ResponseEntity<ApiResponse> GetAllPageable(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements, @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection){

                try {
                    Page<Gender> genders = genderService.GetAllPageable(page, elements, sortBy, sortDirection);
                    ApiResponse response = new ApiResponse(true,SUCCESSFUL_MESSAGE);
                    response.addData("genders", genders.getContent());
                    response.addData("pageable", genders.getPageable());
                    response.addData("totalElements", genders.getTotalElements());
                    response.addData("elementsByPage", genders.getSize());
                    response.addData("totalPages", genders.getTotalPages());
                    return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse(false, "No se ha Recuperado la informac&oacute; de generos"));
                }

    }
}
