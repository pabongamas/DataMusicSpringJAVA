package com.datamusic.datamusic.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Song {
    private Long songId;
    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe ir vacio")
    private String name;
    @NotNull(message = "La duracion no debe ser nula")
    private Long duration;
    @NotNull(message = "El albumId no debe ser nulo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long albumId;
     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Album album;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}
