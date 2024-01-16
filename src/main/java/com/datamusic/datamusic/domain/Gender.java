package com.datamusic.datamusic.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Size;


public class Gender {
    private Long genderId;
    @Size(min = 18, message = "La longitud del nombre debe ser igual o mayor a 18 caracteres")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Album> album;

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

}
