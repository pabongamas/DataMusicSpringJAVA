package com.datamusic.datamusic.web.controller;

import java.util.List;

import java.util.Optional;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.service.GenderService;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Gender> genders = genderService.getAll();
            ApiResponse response = new ApiResponse(true, "Operación exitosa");
            response.addData("genders", genders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No se ha Recuperado la informac&oacute; de generos "));
        }

    }

    // @GetMapping("/{id}")
    // public ResponseEntity <Gender>getGenderByid(@PathVariable("id") Long
    // genderId){
    // return genderService.getGender(genderId).map(gender -> new
    // ResponseEntity<>(gender, HttpStatus.OK))
    // .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGenderById(@PathVariable("id") Long genderId) {
        Optional<Gender> genderOptional = genderService.getGender(genderId);

        if (genderOptional.isPresent()) {
            Gender gender = genderOptional.get();
            ApiResponse response = new ApiResponse(true, "Operación exitosa");
            response.addData("gender", gender);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Género no encontrado", null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Gender gender) {
        try {
            Gender genderCreated =genderService.save(gender);
            ApiResponse response = new ApiResponse(true, "Operación exitosa");
            response.addData("gender", genderCreated);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLGrammarException ex) {
            System.out.println("aca");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error de gramática SQL:"+ex.getSQLException()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long genderId) {
        if (genderService.delete(genderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
